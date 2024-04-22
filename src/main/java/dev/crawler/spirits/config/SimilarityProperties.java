package dev.crawler.spirits.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import dev.crawler.spirits.util.SimilarityAlgorithm;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "similarity")
public class SimilarityProperties {

    private SimilarityAlgorithm algorithm;

}
