package dev.crawler.spirits.service;

import java.util.List;

import dev.crawler.spirits.dto.ItemDto;

public interface ItemService {

    public List<ItemDto> createItems(List<ItemDto> items);

    public void deleteItems();

    public void deleteItems(String version);

}
