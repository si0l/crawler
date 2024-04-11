package dev.crawler.spirits.service.impl;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dev.crawler.spirits.config.ExcelProperties;
import dev.crawler.spirits.dto.ProductDto;
import dev.crawler.spirits.entity.ProductEntity;
import dev.crawler.spirits.mapper.ProductMapper;
import dev.crawler.spirits.repository.ProductRepository;
import dev.crawler.spirits.service.ProductService;
import dev.crawler.spirits.util.ExcelHelper;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ExcelProperties excelProperties;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductDto> createProducts(List<ProductDto> products) {

        List<ProductEntity> resultEntities = productRepository.saveAll(initProductEntities(products));
        return ProductMapper.mapEntityToDtoList(resultEntities);

    }

    @Override
    public List<ProductDto> createProducts(MultipartFile file) throws IOException {

        List<ProductDto> products = ExcelHelper.excelToProducts(file.getInputStream(), excelProperties.getPageName());
        return createProducts(products);

    }

    @Override
    public List<ProductDto> findByVersion(String version) {

        List<ProductEntity> resultEntities = productRepository.findByVersion(version);
        return ProductMapper.mapEntityToDtoList(resultEntities);

    }

    @Override
    public void deleteProducts() {

        productRepository.deleteAll();
        return;

    }

    @Override
    public void deleteProducts(String version) {

        productRepository.removeByVersion(version);
        return;

    }

    private List<ProductEntity> initProductEntities(List<ProductDto> products) {

        List<ProductEntity> entities = ProductMapper.mapDtoToEntityList(products);
        long now = Instant.now().toEpochMilli();
        String version = UUID.randomUUID().toString();
        entities.stream().forEach((entity) -> {
            entity.setDate(now);
            entity.setVersion(version);
        });

        return entities;

    }

}
