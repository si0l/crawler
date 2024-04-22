package dev.crawler.spirits.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.crawler.spirits.config.SimilarityProperties;
import dev.crawler.spirits.dto.ItemDto;
import dev.crawler.spirits.search.service.SearchService;
import dev.crawler.spirits.service.ItemService;
import dev.crawler.spirits.util.SimilarityAlgorithm;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private SimilarityProperties similarityProperties;

    @PostMapping
    public ResponseEntity<List<ItemDto>> searchProducts(@RequestParam String version,
            @RequestParam(required = false) SimilarityAlgorithm algorithm) throws Exception {

        if (algorithm == null) {
            algorithm = similarityProperties.getAlgorithm();
        }

        List<ItemDto> found = searchService.search(version, algorithm);
        List<ItemDto> result = itemService.createItems(found);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
