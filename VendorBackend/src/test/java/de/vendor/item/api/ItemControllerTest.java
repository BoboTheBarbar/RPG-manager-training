package de.vendor.item.api;

import de.vendor.item.model.Item;
import de.vendor.item.model.ItemCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemControllerTest {

    private ItemController itemController;

    @BeforeEach
    void setUp() {
        itemController = new ItemController();
    }

    @Test
    void createItem_ShouldCreateNewItem() {
        // Arrange
        ItemCreate itemCreate = new ItemCreate()
            .name("Test Item")
            .description("Test Description")
            .price(99.99);

        // Act
        ResponseEntity<Item> response = itemController.createItem(itemCreate);

        // Assert
        assertNotNull(response);
        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Test Item", response.getBody().getName());
        assertEquals("Test Description", response.getBody().getDescription());
        assertEquals(99.99, response.getBody().getPrice());
    }

    @Test
    void getAllItems_ShouldReturnEmptyList_WhenNoItems() {
        // Act
        ResponseEntity<List<Item>> response = itemController.getAllItems();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getAllItems_ShouldReturnAllItems() {
        // Arrange
        ItemCreate itemCreate = new ItemCreate()
            .name("Test Item")
            .description("Test Description")
            .price(99.99);
        itemController.createItem(itemCreate);

        // Act
        ResponseEntity<List<Item>> response = itemController.getAllItems();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Test Item", response.getBody().get(0).getName());
    }

    @Test
    void getItemById_ShouldReturnItem_WhenItemExists() {
        // Arrange
        ItemCreate itemCreate = new ItemCreate()
            .name("Test Item")
            .description("Test Description")
            .price(99.99);
        ResponseEntity<Item> createdItem = itemController.createItem(itemCreate);

        // Act
        ResponseEntity<Item> response = itemController.getItemById(createdItem.getBody().getId());

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Test Item", response.getBody().getName());
    }

    @Test
    void getItemById_ShouldReturnNotFound_WhenItemDoesNotExist() {
        // Act
        ResponseEntity<Item> response = itemController.getItemById(999L);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
    }
} 