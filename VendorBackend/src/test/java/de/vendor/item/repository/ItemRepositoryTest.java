package de.vendor.item.repository;

import de.vendor.item.domain.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void findAll_shouldReturnAllItems() {
        // Given
        Item healthPotion = new Item();
        healthPotion.setName("Health Potion");
        healthPotion.setPrice(BigDecimal.valueOf(10.99));
        healthPotion.setDescription("Restores 50 HP");

        Item manaPotion = new Item();
        manaPotion.setName("Mana Potion");
        manaPotion.setPrice(BigDecimal.valueOf(15.99));
        manaPotion.setDescription("Restores 50 MP");

        entityManager.persist(healthPotion);
        entityManager.persist(manaPotion);
        entityManager.flush();

        // When
        List<Item> items = itemRepository.findAll();

        // Then
        assertThat(items).hasSize(2);
        assertThat(items).extracting(Item::getName).containsExactlyInAnyOrder("Health Potion", "Mana Potion");
    }

    @Test
    void findById_shouldReturnItem_whenItemExists() {
        // Given
        Item healthPotion = new Item();
        healthPotion.setName("Health Potion");
        healthPotion.setPrice(BigDecimal.valueOf(10.99));
        healthPotion.setDescription("Restores 50 HP");

        entityManager.persist(healthPotion);
        entityManager.flush();

        // When
        var foundItem = itemRepository.findById(healthPotion.getId());

        // Then
        assertThat(foundItem).isPresent();
        assertThat(foundItem.get().getName()).isEqualTo("Health Potion");
        assertThat(foundItem.get().getPrice()).isEqualByComparingTo(BigDecimal.valueOf(10.99));
        assertThat(foundItem.get().getDescription()).isEqualTo("Restores 50 HP");
    }

    @Test
    void save_shouldPersistItem() {
        // Given
        Item healthPotion = new Item();
        healthPotion.setName("Health Potion");
        healthPotion.setPrice(BigDecimal.valueOf(10.99));
        healthPotion.setDescription("Restores 50 HP");

        // When
        Item savedItem = itemRepository.save(healthPotion);

        // Then
        Item persistedItem = entityManager.find(Item.class, savedItem.getId());
        assertThat(persistedItem).isNotNull();
        assertThat(persistedItem.getName()).isEqualTo("Health Potion");
        assertThat(persistedItem.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(10.99));
        assertThat(persistedItem.getDescription()).isEqualTo("Restores 50 HP");
    }
} 