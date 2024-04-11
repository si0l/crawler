package dev.crawler.spirits.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import dev.crawler.spirits.dto.ProductDto;

public interface ProductService {

    public List<ProductDto> createProducts(List<ProductDto> products);

    public List<ProductDto> createProducts(MultipartFile file) throws IOException;

    public void deleteProducts();

    public void deleteProducts(String version);

    public List<ProductDto> findByVersion(String version);

}
