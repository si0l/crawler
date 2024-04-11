package dev.crawler.spirits.search.service;

import java.util.List;

import dev.crawler.spirits.dto.ItemDto;

public interface SearchService {

    public List<ItemDto> search(String version) throws Exception;
}
