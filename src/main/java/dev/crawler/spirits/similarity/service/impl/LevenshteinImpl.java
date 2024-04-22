package dev.crawler.spirits.similarity.service.impl;

import java.util.List;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Service;

import dev.crawler.spirits.dto.ItemDto;
import dev.crawler.spirits.similarity.service.SimilarityService;

@Service("levenshteinSimilarity")
public class LevenshteinImpl implements SimilarityService {

    @Override
    public ItemDto findTopResult(List<ItemDto> items) {
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        ItemDto topItem = null;
        float topItemDistance = 1;
        // 0 distance means strings are identical
        // 1 means no similarities
        for (ItemDto item : items) {
            if (item.getSearch() == null || item.getLabel() == null) {
                continue;
            }

            int distance = levenshteinDistance.apply(item.getSearch(), item.getLabel());
            float normalizedDistance = distance / Math.max(item.getSearch().length(), item.getLabel().length());
            if (normalizedDistance < topItemDistance) {
                topItemDistance = normalizedDistance;
                topItem = item;
            }
        }

        return topItem;
    }

}
