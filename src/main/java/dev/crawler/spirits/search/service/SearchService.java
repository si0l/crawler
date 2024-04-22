package dev.crawler.spirits.search.service;

import java.util.List;

import dev.crawler.spirits.dto.ItemDto;
import dev.crawler.spirits.util.SimilarityAlgorithm;

public interface SearchService {

    public List<ItemDto> search(String version, SimilarityAlgorithm algorithm) throws Exception;
}
