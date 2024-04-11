package dev.crawler.spirits.search.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import dev.crawler.spirits.config.SiteBaseUrlProperties;
import dev.crawler.spirits.search.service.SearchUrl;

@Service("searchUrlMakro")
public class SearchUrlMakro implements SearchUrl {

    @Autowired
    private SiteBaseUrlProperties siteBaseUrlProperties;

    @Override
    public String generate(String searchText) throws Exception {

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(siteBaseUrlProperties.getMakro())
                .path("/search")
                .query("q={keyword}:relevance:category:JGKA:category:JGKC:category:JGKB:category:JGFA&text={keyword}")
                .buildAndExpand(searchText, searchText);
        // https://www.makro.co.za/search/?q=johnnie%2Bwalker%2Bgold%2Blabel%2Breserve%3Arelevance%3Acategory%3AJGKA%3Acategory%3AJGKC%3Acategory%3AJGKB%3Acategory%3AJGFA&text=johnnie%20walker%20gold%20label%20reserve
        // https://www.makro.co.za/search?q=Johnnie%20Walker%20Gold%20Label%20Reserve:relevance:category:JGKA:category:JGKC:category:JGKB:category:JGFA&text=Johnnie%20Walker%20Gold%20Label%20Reserve
        return uriComponents.encode().toUriString();
    }

}
