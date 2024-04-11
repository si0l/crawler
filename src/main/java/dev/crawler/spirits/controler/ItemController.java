package dev.crawler.spirits.controler;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.crawler.spirits.service.ItemService;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @DeleteMapping
    public ResponseEntity<Void> deleteItems(@RequestParam Optional<String> version) {
        if (version.isPresent()) {
            itemService.deleteItems(version.get());
        } else {
            itemService.deleteItems();
        }
        return ResponseEntity.ok().build();
    }

}
