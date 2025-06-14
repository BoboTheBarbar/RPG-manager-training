package de.vendor.item.service;

import de.vendor.item.domain.DomainItem;
import de.vendor.item.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    private DomainItem healthPotion;

    @BeforeEach
    void setUp() {
        healthPotion = new DomainItem();
        healthPotion.setId(1L);
        healthPotion.setName("Health Potion");
        healthPotion.setDescription("Restores 50 HP");
        healthPotion.setPrice(10.99);
    }

    @Test
    void getAllItems_shouldReturnAllItems() {
        // Given
        DomainItem manaPotion = new DomainItem();
        manaPotion.setId(2L);
        manaPotion.setName("Mana Potion");
        manaPotion.setDescription("Restores 50 MP");
        manaPotion.setPrice(15.99);

        when(itemRepository.findAll()).thenReturn(Arrays.asList(healthPotion, manaPotion));

        // When
        List<DomainItem> items = itemService.getAllItems();

        // Then
        assertThat(items).hasSize(2);
        assertThat(items).extracting("name").containsExactlyInAnyOrder("Health Potion", "Mana Potion");
    }

    @Test
    void getItemById_shouldReturnItem_whenItemExists() {
        // Given
        when(itemRepository.findById(1L)).thenReturn(Optional.of(healthPotion));

        // When
        Optional<DomainItem> foundItem = itemService.getItemById(1L);

        // Then
        assertThat(foundItem).isPresent();
        assertThat(foundItem.get().getName()).isEqualTo("Health Potion");
        assertThat(foundItem.get().getPrice()).isEqualTo(10.99);
    }

    @Test
    void createItem_shouldReturnCreatedItem() {
        // Given
        DomainItem itemToSave = new DomainItem();
        itemToSave.setName("Health Potion");
        itemToSave.setDescription("Restores 50 HP");
        itemToSave.setPrice(10.99);

        DomainItem savedItem = new DomainItem();
        savedItem.setId(1L);
        savedItem.setName("Health Potion");
        savedItem.setDescription("Restores 50 HP");
        savedItem.setPrice(10.99);

        when(itemRepository.save(any(DomainItem.class))).thenReturn(savedItem);

        // When
        DomainItem createdItem = itemService.createItem(itemToSave);

        // Then
        assertThat(createdItem.getId()).isEqualTo(1L);
        assertThat(createdItem.getName()).isEqualTo("Health Potion");
        assertThat(createdItem.getPrice()).isEqualTo(10.99);
    }
} 