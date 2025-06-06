package de.vendor.item.service;

import de.vendor.item.domain.DomainItem;
import de.vendor.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    public List<DomainItem> getAllItems() {
        return itemRepository.findAll();
    }

    public Optional<DomainItem> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    @Transactional
    public DomainItem createItem(DomainItem item) {
        return itemRepository.save(item);
    }
} 