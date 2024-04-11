package dev.crawler.spirits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import dev.crawler.spirits.entity.ItemEntity;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    @Transactional
    Long removeByProductVersion(String version);

}
