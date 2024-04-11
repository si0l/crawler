package dev.crawler.spirits.controler;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dev.crawler.spirits.dto.ProductDto;
import dev.crawler.spirits.exception.ResourceException;
import dev.crawler.spirits.service.ProductService;
import dev.crawler.spirits.util.ExcelHelper;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<List<ProductDto>> createProducts(@RequestBody List<ProductDto> products) {
        List<ProductDto> result = productService.createProducts(products);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/upload")
    public ResponseEntity<List<ProductDto>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (ExcelHelper.isExcelFormat(file)) {
            List<ProductDto> result = productService.createProducts(file);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else {
            throw new ResourceException(HttpStatus.BAD_REQUEST, "Excel format is expected.");
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProducts(@RequestParam Optional<String> version) {
        if (version.isPresent()) {
            productService.deleteProducts(version.get());
        } else {
            productService.deleteProducts();
        }
        return ResponseEntity.ok().build();
    }

}
