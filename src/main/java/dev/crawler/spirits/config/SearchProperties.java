package dev.crawler.spirits.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "search")
public class SearchProperties {

    private Integer retriesCount;
    private Long timeout;

    @Value("${search.selector.makro}")
    private String makroSelector;

    @Value("${search.selector.checkers}")
    private String checkersSelector;

    @Value("${search.selector.ngf}")
    private String ngfSelector;

}
