package dev.crawler.spirits.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "products")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String version;

    @Column(nullable = false)
    private long date;

    private String initialText;
    private String brand;
    @Column(nullable = false)
    private String search;

}
