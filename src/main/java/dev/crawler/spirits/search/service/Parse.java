package dev.crawler.spirits.search.service;

import java.util.List;

import org.openqa.selenium.WebElement;

import dev.crawler.spirits.dto.ItemDto;
import dev.crawler.spirits.dto.ProductDto;

public interface Parse {

    public List<ItemDto> getItems(List<WebElement> elements, ProductDto productDto, String url, long date);

}
