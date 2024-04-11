package dev.crawler.spirits.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PriceHelper {

    public static Float parsePrice(Source source, String price) {

        if (price == null) {
            return null;
        }

        String processed = price.trim();
        if (processed.equals("")) {
            return null;
        }

        processed = processed.replaceAll("[, R r]", "");

        if (Source.MAKRO == source && processed.endsWith("00")) {
            processed = processed.substring(0, processed.length() - 2) + ".00";
        }

        if (!processed.contains(".")) {
            processed = processed + ".00";
        }

        try {
            Float value = Float.valueOf(processed);
            return value;
        } catch (NumberFormatException ex) {
            log.warn("Price format error: " + price);
        }
        return null;
    }
}