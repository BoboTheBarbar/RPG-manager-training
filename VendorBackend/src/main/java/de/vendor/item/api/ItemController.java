package de.vendor.item.api;

import de.vendor.item.model.Item;
import de.vendor.item.model.ItemCreate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ItemController implements ApiApi {
    
    private final ConcurrentHashMap<Long, Item> items = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public ResponseEntity<Item> createItem(ItemCreate itemCreate) {
        Item newItem = new Item()
            .id(idGenerator.getAndIncrement())
            .name(itemCreate.getName())
            .description(itemCreate.getDescription())
            .price(itemCreate.getPrice());
        
        items.put(newItem.getId(), newItem);
        return ResponseEntity.status(201).body(newItem);
    }

    @Override
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(new ArrayList<>(items.values()));
    }

    @Override
    public ResponseEntity<Item> getItemById(Long id) {
        Item item = items.get(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(item);
    }
} 