package dev.crawler.spirits.search.service.impl;

import java.util.List;
import java.util.Objects;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.crawler.spirits.dto.ItemDto;
import dev.crawler.spirits.dto.ProductDto;
import dev.crawler.spirits.search.service.Parse;
import dev.crawler.spirits.util.PriceHelper;
import dev.crawler.spirits.util.Source;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("parseCheckers")
public class ParseCheckers implements Parse {

    @Override
    public List<ItemDto> getItems(List<WebElement> elements, ProductDto productDto, String url, long date) {

        return elements.stream().map(element -> {
            try {
                String propsJson = element.getAttribute("data-product-ga");
                JsonNode node = new ObjectMapper().readTree(propsJson);
                String name = node.has("name") ? node.get("name").asText() : null;
                String price = node.has("price") ? node.get("price").asText() : null;
                if (name != null && price != null && !name.equals("") && !price.equals("")) {
                    log.info("Found label: " + name + ". price: " + price);
                    ItemDto item = ItemDto.builder()
                            .brand(productDto.getBrand())
                            .date(date)
                            .initialText(productDto.getInitialText())
                            .productVersion(productDto.getVersion())
                            .search(productDto.getSearch())
                            .url(url)
                            .price(price)
                            .parsedPrice(PriceHelper.parsePrice(Source.CHECKERS, price))
                            .isException(false)
                            .isFound(true)
                            .label(name)
                            .source(Source.CHECKERS)
                            .isException(false)
                            .isFound(true)
                            .build();

                    return item;
                }

            } catch (NoSuchElementException | JsonProcessingException ex) {

                log.warn("Element was not found: " + ex.getMessage());
                ItemDto item = ItemDto.builder()
                        .date(date)
                        .initialText(productDto.getInitialText())
                        .productVersion(productDto.getVersion())
                        .search(productDto.getSearch())
                        .url(url)
                        .isException(false)
                        .isFound(false)
                        .source(Source.CHECKERS)
                        .info("Empty page results, elements not found.")
                        .build();
                return item;

            }

            return null;
        }).filter(Objects::nonNull).toList();
    }

}
