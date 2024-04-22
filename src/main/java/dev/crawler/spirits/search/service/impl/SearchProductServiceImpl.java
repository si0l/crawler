package dev.crawler.spirits.search.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dev.crawler.spirits.config.SearchProperties;
import dev.crawler.spirits.dto.ItemDto;
import dev.crawler.spirits.dto.ProductDto;
import dev.crawler.spirits.search.service.Parse;
import dev.crawler.spirits.search.service.SearchProductService;
import dev.crawler.spirits.search.service.SearchUrl;
import dev.crawler.spirits.similarity.service.SimilarityService;
import dev.crawler.spirits.util.SimilarityAlgorithm;
import dev.crawler.spirits.util.Source;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SearchProductServiceImpl implements SearchProductService {

    @Autowired
    private SearchProperties searchProperties;

    @Autowired
    @Qualifier("jaroWinklerSimilarity")
    private SimilarityService jaroWinklerSimilarity;

    @Autowired
    @Qualifier("levenshteinSimilarity")
    private SimilarityService levenshteinSimilarity;

    @Autowired
    @Qualifier("parseMakro")
    private Parse parseMakro;

    @Autowired
    @Qualifier("parseCheckers")
    private Parse parseCheckers;

    @Autowired
    @Qualifier("parseNgf")
    private Parse parseNgf;

    @Autowired
    @Qualifier("searchUrlMakro")
    private SearchUrl searchUrlMakro;

    @Autowired
    @Qualifier("searchUrlCheckers")
    private SearchUrl searchUrlCheckers;

    @Autowired
    @Qualifier("searchUrlNgf")
    private SearchUrl searchUrlNgf;

    public List<ItemDto> search(ProductDto product, long date, SimilarityAlgorithm algorithm) throws Exception {
        List<ItemDto> result = new ArrayList<>();
        WebDriver driver = null;
        try {

            String urlMakro = generateUrl(Source.MAKRO, product.getSearch());
            log.info("Generated search URL for makro: " + urlMakro);
            String urlCheckers = generateUrl(Source.CHECKERS, product.getSearch());
            log.info("Generated search URL for checkers: " + urlCheckers);
            String urlNgf = generateUrl(Source.NGF, product.getSearch());
            log.info("Generated search URL for ngf: " + urlNgf);

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            driver = new ChromeDriver(options);

            try {
                List<WebElement> makroElements = getData(driver, urlMakro, searchProperties.getMakroSelector());
                List<ItemDto> parsedResults = parseMakro.getItems(makroElements, product, urlMakro, date);
                List<ItemDto> ratedResults = getRatedResults(parsedResults, algorithm);
                result.addAll(ratedResults);
            } catch (Exception ex) {
                String message = "Document retrieval failure for makro: " + ex.getMessage();
                log.error(message, ex);
                ItemDto item = ItemDto.builder()
                        .date(date)
                        .initialText(product.getInitialText())
                        .productVersion(product.getVersion())
                        .search(product.getSearch())
                        .url(urlMakro)
                        .isException(true)
                        .isFound(false)
                        .source(Source.MAKRO)
                        .info(message.substring(0, Math.min(message.length(), 1500)))
                        .build();
                result.add(item);
            }

            try {
                List<WebElement> checkersElements = getData(driver, urlCheckers,
                        searchProperties.getCheckersSelector());
                List<ItemDto> parsedResults = parseCheckers.getItems(checkersElements, product, urlCheckers, date);
                List<ItemDto> ratedResults = getRatedResults(parsedResults, algorithm);
                result.addAll(ratedResults);
            } catch (Exception ex) {
                String message = "Document retrieval failure for checkers: " + ex.getMessage();
                log.error(message);
                ItemDto item = ItemDto.builder()
                        .date(date)
                        .initialText(product.getInitialText())
                        .productVersion(product.getVersion())
                        .search(product.getSearch())
                        .url(urlCheckers)
                        .isException(true)
                        .isFound(false)
                        .source(Source.CHECKERS)
                        .info(message.substring(0, Math.min(message.length(), 1500)))
                        .build();
                result.add(item);
            }

            try {
                List<WebElement> ngfElements = getData(driver, urlNgf, searchProperties.getNgfSelector());
                List<ItemDto> parsedResults = parseNgf.getItems(ngfElements, product, urlNgf, date);
                List<ItemDto> ratedResults = getRatedResults(parsedResults, algorithm);
                result.addAll(ratedResults);
            } catch (Exception ex) {
                String message = "Document retrieval failure for ngf: " + ex.getMessage();
                log.error(message);
                ItemDto item = ItemDto.builder()
                        .date(date)
                        .initialText(product.getInitialText())
                        .productVersion(product.getVersion())
                        .search(product.getSearch())
                        .url(urlNgf)
                        .isException(true)
                        .isFound(false)
                        .source(Source.NGF)
                        .info(message.substring(0, Math.min(message.length(), 1500)))
                        .build();
                result.add(item);
            }
        } catch (Exception ex) {
            ItemDto item = ItemDto.builder()
                    .date(date)
                    .initialText(product.getInitialText())
                    .productVersion(product.getVersion())
                    .search(product.getSearch())
                    .isException(true)
                    .isFound(false)
                    .info("Error occurred during product search, id: " + product.getId()
                            + ", any or all of 3 sites results may be missing, exception: " + ex.getMessage())
                    .build();
            result.add(item);
            log.error("Product search exception, product id: " + product.getId(), ex);
        } finally {
            if (driver != null) {
                try {
                    driver.quit();
                } catch (Exception ex) {

                }
            }
        }

        return result;

    }

    private List<WebElement> getData(WebDriver driver, String url, String selector) throws InterruptedException {

        driver.get(url);
        List<WebElement> elements = new ArrayList<>();
        int iteration = 0;
        boolean found = false;
        while (!found && iteration++ < searchProperties.getRetriesCount()) {
            Thread.sleep(searchProperties.getTimeout());
            // driver.findElement(By.cssSelector("div[data-testid=\"plp_flat_list\"]"));
            elements = driver
                    .findElements(By.cssSelector(selector));
            if (elements.size() > 0) {
                found = true;
                log.info("Found elements: " + elements.size());
            }
        }
        if (!found) {
            log.info("Nothing found");
        }

        return elements;

    }

    private List<ItemDto> getRatedResults(List<ItemDto> items, SimilarityAlgorithm algorithm) {

        if (algorithm == SimilarityAlgorithm.ALL || items.size() == 0) {
            return items;
        }

        ItemDto topResult = switch (algorithm) {
            case DEFAULT -> items.size() > 0 ? items.get(0) : null;
            case LEVENSHTEIN -> levenshteinSimilarity.findTopResult(items);
            case JARO_WINKLER -> jaroWinklerSimilarity.findTopResult(items);
            default -> throw new RuntimeException("Unknown algorithm value");
        };

        return Arrays.asList(topResult);
    }

    private String generateUrl(Source site, String search) throws Exception {
        return switch (site) {
            case MAKRO ->
                searchUrlMakro.generate(search);
            case CHECKERS ->
                searchUrlCheckers.generate(search);
            case NGF ->
                searchUrlNgf.generate(search);
            default ->
                throw new Exception("Site not supported");
        };
    }

}
