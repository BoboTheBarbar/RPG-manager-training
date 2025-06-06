package de.vendor.item.repository;

import de.vendor.item.domain.DomainItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

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
        DomainItem healthPotion = new DomainItem();
        healthPotion.setName("Health Potion");
        healthPotion.setDescription("Restores 50 HP");
        healthPotion.setPrice(10.99);
        entityManager.persist(healthPotion);

        DomainItem manaPotion = new DomainItem();
        manaPotion.setName("Mana Potion");
        manaPotion.setDescription("Restores 50 MP");
        manaPotion.setPrice(15.99);
        entityManager.persist(manaPotion);

        entityManager.flush();

        // When
        List<DomainItem> items = itemRepository.findAll();

        // Then
        assertThat(items).hasSize(2);
        assertThat(items).extracting("name").containsExactlyInAnyOrder("Health Potion", "Mana Potion");
    }

    @Test
    void findById_shouldReturnItem_whenItemExists() {
        // Given
        DomainItem healthPotion = new DomainItem();
        healthPotion.setName("Health Potion");
        healthPotion.setDescription("Restores 50 HP");
        healthPotion.setPrice(10.99);
        Long id = entityManager.persist(healthPotion).getId();
        entityManager.flush();

        // When
        Optional<DomainItem> foundItem = itemRepository.findById(id);

        // Then
        assertThat(foundItem).isPresent();
        assertThat(foundItem.get().getName()).isEqualTo("Health Potion");
        assertThat(foundItem.get().getPrice()).isEqualTo(10.99);
    }

    @Test
    void save_shouldPersistItem() {
        // Given
        DomainItem healthPotion = new DomainItem();
        healthPotion.setName("Health Potion");
        healthPotion.setDescription("Restores 50 HP");
        healthPotion.setPrice(10.99);

        // When
        DomainItem persistedItem = itemRepository.save(healthPotion);

        // Then
        assertThat(persistedItem.getId()).isNotNull();
        assertThat(persistedItem.getName()).isEqualTo("Health Potion");
        assertThat(persistedItem.getPrice()).isEqualTo(10.99);
    }
} 