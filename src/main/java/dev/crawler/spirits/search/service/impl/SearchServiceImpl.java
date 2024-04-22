package dev.crawler.spirits.search.service.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import dev.crawler.spirits.config.PoolProperties;
import dev.crawler.spirits.dto.ItemDto;
import dev.crawler.spirits.dto.ProductDto;
import dev.crawler.spirits.search.service.SearchProductService;
import dev.crawler.spirits.search.service.SearchService;
import dev.crawler.spirits.service.ProductService;
import dev.crawler.spirits.util.ExecutorHelper;
import dev.crawler.spirits.util.SimilarityAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private PoolProperties poolProperties;

    @Autowired
    private ProductService productService;

    @Autowired
    private SearchProductService searchProductService;

    @Override
    public List<ItemDto> search(String version, SimilarityAlgorithm algorithm) throws Exception {

        log.info("Similarity algorithm applied: " + algorithm);

        List<ProductDto> products = productService.findByVersion(version);
        ThreadPoolTaskExecutor executor = ExecutorHelper.taskExecutor(poolProperties);

        List<ItemDto> result = new ArrayList<>();
        Instant dateStart = Instant.now();
        List<Future<List<ItemDto>>> futureResult = new ArrayList<>();
        for (ProductDto product : products) {
            Callable<List<ItemDto>> callable = () -> searchProductService.search(product, dateStart.toEpochMilli(),
                    algorithm);
            Future<List<ItemDto>> future = executor.submit(callable);
            futureResult.add(future);
        }

        for (Future<List<ItemDto>> future : futureResult) {
            try {
                List<ItemDto> productResult = future.get();
                result.addAll(productResult);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        Instant dateEnd = Instant.now();
        log.info("Search finished, items found: " + result.size() + ", time taken, seconds: "
                + Duration.between(dateStart, dateEnd).toSeconds());

        return result;
    }

}
