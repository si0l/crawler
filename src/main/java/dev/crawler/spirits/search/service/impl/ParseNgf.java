package dev.crawler.spirits.search.service.impl;

import java.util.List;
import java.util.Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import dev.crawler.spirits.dto.ItemDto;
import dev.crawler.spirits.dto.ProductDto;
import dev.crawler.spirits.search.service.Parse;
import dev.crawler.spirits.util.PriceHelper;
import dev.crawler.spirits.util.Source;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("parseNgf")
public class ParseNgf implements Parse {

    @Override
    public List<ItemDto> getItems(List<WebElement> elements, ProductDto productDto, String url, long date) {
        return elements.stream().map(element -> {
            WebElement priceElement;
            WebElement labelElement;
            try {
                priceElement = element.findElement(By.cssSelector("div.price-wrapper bdi"));
                labelElement = element.findElement(By.cssSelector("div.title-wrapper a"));
            } catch (NoSuchElementException ex) {
                log.warn("Element was not found: " + ex.getMessage());
                ItemDto item = ItemDto.builder()
                        .date(date)
                        .initialText(productDto.getInitialText())
                        .productVersion(productDto.getVersion())
                        .search(productDto.getSearch())
                        .url(url)
                        .isException(false)
                        .isFound(false)
                        .source(Source.NGF)
                        .info("Empty page results, elements not found.")
                        .build();
                return item;
            }
            log.info("Found label: " + labelElement.getText() + ". price: " + priceElement.getText());
            String price = priceElement.getText();
            ItemDto item = ItemDto.builder()
                    .brand(productDto.getBrand())
                    .date(date)
                    .initialText(productDto.getInitialText())
                    .productVersion(productDto.getVersion())
                    .search(productDto.getSearch())
                    .url(url)
                    .price(price)
                    .parsedPrice(PriceHelper.parsePrice(Source.NGF, price))
                    .isException(false)
                    .isFound(true)
                    .label(labelElement.getText())
                    .source(Source.NGF)
                    .build();
            return item;
        }).filter(Objects::nonNull).toList();
    }

}
