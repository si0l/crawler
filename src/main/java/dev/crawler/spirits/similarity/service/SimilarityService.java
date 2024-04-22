package dev.crawler.spirits.similarity.service;

import java.util.List;

import dev.crawler.spirits.dto.ItemDto;

public interface SimilarityService {

    public ItemDto findTopResult(List<ItemDto> items);

}
