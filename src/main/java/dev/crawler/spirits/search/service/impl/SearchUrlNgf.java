package dev.crawler.spirits.search.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import dev.crawler.spirits.config.SiteBaseUrlProperties;
import dev.crawler.spirits.search.service.SearchUrl;

@Service("searchUrlNgf")
public class SearchUrlNgf implements SearchUrl {

    @Autowired
    private SiteBaseUrlProperties siteBaseUrlProperties;

    @Override
    public String generate(String searchText) throws Exception {

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(siteBaseUrlProperties.getNgf())
                .path("")
                .query("s={keyword}&post_type=product&dgwt_wcas=1&filters=product_cat")
                .buildAndExpand(searchText);
        // https://www.ngf.co.za/?s=Johnnie+Walker+Gold+Label+Reserve&post_type=product&dgwt_wcas=1&filters=product_cat[1736]
        String encodedUrl = uriComponents.encode().toUriString();
        return encodedUrl + "[1736]";
    }

}
