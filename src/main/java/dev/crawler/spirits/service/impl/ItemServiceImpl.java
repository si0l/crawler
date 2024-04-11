package dev.crawler.spirits.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.crawler.spirits.dto.ItemDto;
import dev.crawler.spirits.entity.ItemEntity;
import dev.crawler.spirits.mapper.ItemMapper;
import dev.crawler.spirits.repository.ItemRepository;
import dev.crawler.spirits.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<ItemDto> createItems(List<ItemDto> items) {

        List<ItemEntity> resultEntities = itemRepository.saveAll(ItemMapper.mapDtoToEntityList(items));
        return ItemMapper.mapEntityToDtoList(resultEntities);

    }

    @Override
    public void deleteItems() {

        itemRepository.deleteAll();
        return;

    }

    @Override
    public void deleteItems(String version) {

        itemRepository.removeByProductVersion(version);
        return;

    }

}
