package dev.crawler.spirits.search.service;

import java.util.List;

import dev.crawler.spirits.dto.ItemDto;
import dev.crawler.spirits.dto.ProductDto;

public interface SearchProductService {

    public List<ItemDto> search(ProductDto product, long date) throws Exception;
    
}
