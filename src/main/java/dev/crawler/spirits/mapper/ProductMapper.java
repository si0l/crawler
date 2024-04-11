package dev.crawler.spirits.mapper;

import java.util.List;

import dev.crawler.spirits.dto.ProductDto;
import dev.crawler.spirits.entity.ProductEntity;

public class ProductMapper {

    public static ProductDto mapEntityToDto(ProductEntity item) {
        return ProductDto.builder().id(item.getId()).version(item.getVersion())
                .date(item.getDate()).initialText(item.getInitialText()).brand(item.getBrand())
                .search(item.getSearch()).build();
    }

    public static ProductEntity mapDtoToEntity(ProductDto item) {
        return ProductEntity.builder().id(item.getId()).version(item.getVersion())
                .date(item.getDate()).initialText(item.getInitialText()).brand(item.getBrand())
                .search(item.getSearch()).build();
    }

    public static List<ProductEntity> mapDtoToEntityList(List<ProductDto> items) {
        return items.stream().map((item) -> ProductMapper.mapDtoToEntity(item)).toList();
    }

    public static List<ProductDto> mapEntityToDtoList(List<ProductEntity> items) {
        return items.stream().map((item) -> ProductMapper.mapEntityToDto(item)).toList();
    }

}
