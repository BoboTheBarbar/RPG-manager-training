package de.vendor.item.api;

import de.vendor.item.adapter.ItemAdapter;
import de.vendor.item.domain.DomainItem;
import de.vendor.item.model.Item;
import de.vendor.item.model.ItemCreate;
import de.vendor.item.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ItemControllerTest {

    private ItemController itemController;
    
    @Mock
    private ItemService itemService;
    
    private ItemAdapter itemAdapter;  // Echte Instanz statt Mock

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        itemAdapter = new ItemAdapter();  // Echte Instanz erstellen
        itemController = new ItemController(itemService, itemAdapter);
    }

    @Test
    void createItem_ShouldCreateNewItem() {
        // Arrange
        ItemCreate itemCreate = new ItemCreate()
            .name("Test Item")
            .description("Test Description")
            .price(99.99);
        
        DomainItem savedDomainItem = new DomainItem();
        savedDomainItem.setId(1L);
        savedDomainItem.setName("Test Item");
        savedDomainItem.setDescription("Test Description");
        savedDomainItem.setPrice(99.99);
        
        when(itemService.createItem(any(DomainItem.class))).thenReturn(savedDomainItem);

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
        // Arrange
        when(itemService.getAllItems()).thenReturn(Collections.emptyList());
        
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
        DomainItem domainItem = new DomainItem();
        domainItem.setId(1L);
        domainItem.setName("Test Item");
        domainItem.setDescription("Test Description");
        domainItem.setPrice(99.99);
        
        when(itemService.getAllItems()).thenReturn(Arrays.asList(domainItem));

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
        DomainItem domainItem = new DomainItem();
        domainItem.setId(1L);
        domainItem.setName("Test Item");
        domainItem.setDescription("Test Description");
        domainItem.setPrice(99.99);
        
        when(itemService.getItemById(1L)).thenReturn(Optional.of(domainItem));

        // Act
        ResponseEntity<Item> response = itemController.getItemById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Test Item", response.getBody().getName());
    }

    @Test
    void getItemById_ShouldReturnNotFound_WhenItemDoesNotExist() {
        // Arrange
        when(itemService.getItemById(999L)).thenReturn(Optional.empty());
        
        // Act
        ResponseEntity<Item> response = itemController.getItemById(999L);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
    }
}
