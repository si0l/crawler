package dev.crawler.spirits.search.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import dev.crawler.spirits.config.SiteBaseUrlProperties;
import dev.crawler.spirits.search.service.SearchUrl;

@Service("searchUrlCheckers")
public class SearchUrlCheckers implements SearchUrl {

    @Autowired
    private SiteBaseUrlProperties siteBaseUrlProperties;

    @Override
    public String generate(String searchText) throws Exception {

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(siteBaseUrlProperties.getCheckers())
                .path("/search")
                .query("q={keyword}:searchRelevance:allCategories:spirits_and_liqueurs:browseAllStoresFacetOff:browseAllStoresFacetOff:browseAllStoresFacet:browseAllStoresFacet")
                .buildAndExpand(searchText);
        // https://www.checkers.co.za/search?q=Johnnie%20Walker%20Gold%20Label%20Reserve:searchRelevance:allCategories:spirits_and_liqueurs:browseAllStoresFacetOff:browseAllStoresFacetOff:browseAllStoresFacet:browseAllStoresFacet#page=0
        String encodedResult = uriComponents.encode().toUriString();
        return encodedResult + "#page=0";
    }

}
