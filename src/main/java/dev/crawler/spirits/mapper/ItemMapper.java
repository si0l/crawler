package dev.crawler.spirits.mapper;

import java.util.List;

import dev.crawler.spirits.dto.ItemDto;
import dev.crawler.spirits.entity.ItemEntity;

public class ItemMapper {

    public static ItemDto mapEntityToDto(ItemEntity item) {
        return ItemDto.builder().id(item.getId()).productVersion(item.getProductVersion()).url(item.getUrl())
                .date(item.getDate()).initialText(item.getInitialText()).brand(item.getBrand())
                .search(item.getSearch()).price(item.getPrice()).label(item.getLabel()).source(item.getSource())
                .parsedPrice(item.getParsedPrice()).isFound(item.isFound()).isException(item.isException())
                .info(item.getInfo())
                .build();
    }

    public static ItemEntity mapDtoToEntity(ItemDto item) {
        return ItemEntity.builder().id(item.getId()).productVersion(item.getProductVersion())
                .url(item.getUrl())
                .date(item.getDate()).initialText(item.getInitialText()).brand(item.getBrand())
                .search(item.getSearch()).price(item.getPrice()).label(item.getLabel()).source(item.getSource())
                .parsedPrice(item.getParsedPrice()).isFound(item.isFound()).isException(item.isException())
                .info(item.getInfo())
                .build();
    }

    public static List<ItemEntity> mapDtoToEntityList(List<ItemDto> items) {
        return items.stream().map((item) -> ItemMapper.mapDtoToEntity(item)).toList();
    }

    public static List<ItemDto> mapEntityToDtoList(List<ItemEntity> items) {
        return items.stream().map((item) -> ItemMapper.mapEntityToDto(item)).toList();
    }
}
