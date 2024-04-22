package dev.crawler.spirits.similarity.service.impl;

import java.util.List;

import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.springframework.stereotype.Service;

import dev.crawler.spirits.dto.ItemDto;
import dev.crawler.spirits.similarity.service.SimilarityService;

@Service("jaroWinklerSimilarity")
public class JaroWinklerImpl implements SimilarityService {

    @Override
    public ItemDto findTopResult(List<ItemDto> items) {
        JaroWinklerSimilarity similarity = new JaroWinklerSimilarity();
        ItemDto topItem = null;
        double topItemDistance = 1.0;
        // 0 distance means strings are identical
        // 1 means no similarities
        for (ItemDto item : items) {
            if (item.getSearch() == null || item.getLabel() == null) {
                continue;
            }
            double distance = similarity.apply(item.getSearch(), item.getLabel());
            if (distance < topItemDistance) {
                topItemDistance = distance;
                topItem = item;
            }
        }

        return topItem;
    }

}
