package de.vendor.item.service;

import de.vendor.item.domain.Item;
import de.vendor.item.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    private ItemService itemService;

    @BeforeEach
    void setUp() {
        itemService = new ItemService(itemRepository);
    }

    @Test
    void getAllItems_shouldReturnAllItems() {
        // Given
        Item healthPotion = new Item();
        healthPotion.setId(1L);
        healthPotion.setName("Health Potion");
        healthPotion.setPrice(BigDecimal.valueOf(10.99));
        healthPotion.setDescription("Restores 50 HP");

        when(itemRepository.findAll()).thenReturn(List.of(healthPotion));

        // When
        List<Item> items = itemService.getAllItems();

        // Then
        assertThat(items).hasSize(1);
        assertThat(items.get(0).getName()).isEqualTo("Health Potion");
    }

    @Test
    void getItemById_shouldReturnItem_whenItemExists() {
        // Given
        Item healthPotion = new Item();
        healthPotion.setId(1L);
        healthPotion.setName("Health Potion");
        healthPotion.setPrice(BigDecimal.valueOf(10.99));
        healthPotion.setDescription("Restores 50 HP");

        when(itemRepository.findById(1L)).thenReturn(Optional.of(healthPotion));

        // When
        Optional<Item> foundItem = itemService.getItemById(1L);

        // Then
        assertThat(foundItem).isPresent();
        assertThat(foundItem.get().getName()).isEqualTo("Health Potion");
    }

    @Test
    void createItem_shouldReturnSavedItem() {
        // Given
        Item itemToSave = new Item();
        itemToSave.setName("Health Potion");
        itemToSave.setPrice(BigDecimal.valueOf(10.99));
        itemToSave.setDescription("Restores 50 HP");

        Item savedItem = new Item();
        savedItem.setId(1L);
        savedItem.setName("Health Potion");
        savedItem.setPrice(BigDecimal.valueOf(10.99));
        savedItem.setDescription("Restores 50 HP");

        when(itemRepository.save(any(Item.class))).thenReturn(savedItem);

        // When
        Item result = itemService.createItem(itemToSave);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Health Potion");
    }
} 