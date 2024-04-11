package dev.crawler.spirits.dto;

import dev.crawler.spirits.util.Source;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {

    private Long id;
    private String productVersion;
    private long date;
    private String url;
    private String initialText;
    private String brand;
    @NotBlank
    private String search;
    private String price;
    private String label;
    @NotBlank
    private Source source;

    private float parsedPrice;

    @NotBlank
    private boolean isFound;

    @NotBlank
    private boolean isException;

    private String info;

}
