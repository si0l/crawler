package dev.crawler.spirits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import dev.crawler.spirits.entity.ProductEntity;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByVersion(String version);

    @Transactional
    Long removeByVersion(String version);

}
