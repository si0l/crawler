package dev.crawler.spirits.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "url.base")
public class SiteBaseUrlProperties {

    private String makro;
    private String checkers;
    private String ngf;

}
