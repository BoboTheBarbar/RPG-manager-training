package de.vendor.item.api;

import de.vendor.item.adapter.ItemAdapter;
import de.vendor.item.domain.DomainItem;
import de.vendor.item.model.Item;
import de.vendor.item.model.ItemCreate;
import de.vendor.item.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ItemController implements ApiApi {

    private final ItemService itemService;
    private final ItemAdapter itemAdapter;

    public ItemController(ItemService itemService, ItemAdapter itemAdapter) {
        this.itemService = itemService;
        this.itemAdapter = itemAdapter;
    }

    @Override
    public ResponseEntity<Item> createItem(ItemCreate itemCreate) {
        // Konvertiere ItemCreate zu Domain-Item
        DomainItem domainItem = itemAdapter.toDomainItem(itemCreate);
        
        // Speichere über den Service
        DomainItem savedDomainItem = itemService.createItem(domainItem);
        
        // Konvertiere zurück zu API-Item für die Antwort
        Item responseItem = itemAdapter.toApiItem(savedDomainItem);

        return ResponseEntity.status(201).body(responseItem);
    }

    @Override
    public ResponseEntity<List<Item>> getAllItems() {
        List<DomainItem> domainItems = itemService.getAllItems();
        List<Item> apiItems = itemAdapter.toApiItems(domainItems);
        
        return ResponseEntity.ok(apiItems);
    }

    @Override
    public ResponseEntity<Item> getItemById(Long id) {
        // Hole Domain-Item vom Service
        Optional<DomainItem> domainItemOpt = itemService.getItemById(id);
        
        if (domainItemOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        // Konvertiere zu API-Item
        Item apiItem = itemAdapter.toApiItem(domainItemOpt.get());
        
        return ResponseEntity.ok(apiItem);
    }
}
