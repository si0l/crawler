package dev.crawler.spirits.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import dev.crawler.spirits.util.PoolType;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "pool")
public class PoolProperties {

    private PoolType type;
    private Integer size;

}
