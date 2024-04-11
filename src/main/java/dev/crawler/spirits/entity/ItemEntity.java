package dev.crawler.spirits.entity;

import dev.crawler.spirits.util.Source;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "items")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "product_version", nullable = false)
    private String productVersion;

    @Column(nullable = false)
    private long date;

    private String url;

    private String initialText;

    private String brand;

    @Column(nullable = false)
    private String search;

    private String price;

    private String label;

    private Source source;

    private float parsedPrice;

    @Column(nullable = false)
    private boolean isFound;

    @Column(nullable = false)
    private boolean isException;

    @Column(length = 2000)
    private String info;
}
