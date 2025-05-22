package de.vendor.item.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.vendor.item.domain.Item;
import de.vendor.item.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemService itemService;

    @Test
    void getAllItems_shouldReturnItems() throws Exception {
        // Given
        Item item = new Item();
        item.setId(1L);
        item.setName("Health Potion");
        item.setPrice(BigDecimal.valueOf(10.99));
        item.setDescription("Restores 50 HP");

        when(itemService.getAllItems()).thenReturn(List.of(item));

        // When & Then
        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Health Potion"))
                .andExpect(jsonPath("$[0].price").value(10.99))
                .andExpect(jsonPath("$[0].description").value("Restores 50 HP"));
    }

    @Test
    void getItemById_shouldReturnItem_whenItemExists() throws Exception {
        // Given
        Item item = new Item();
        item.setId(1L);
        item.setName("Health Potion");
        item.setPrice(BigDecimal.valueOf(10.99));
        item.setDescription("Restores 50 HP");

        when(itemService.getItemById(1L)).thenReturn(Optional.of(item));

        // When & Then
        mockMvc.perform(get("/api/items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Health Potion"))
                .andExpect(jsonPath("$.price").value(10.99))
                .andExpect(jsonPath("$.description").value("Restores 50 HP"));
    }

    @Test
    void getItemById_shouldReturn404_whenItemDoesNotExist() throws Exception {
        // Given
        when(itemService.getItemById(1L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/items/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createItem_shouldReturnCreatedItem() throws Exception {
        // Given
        Item itemToCreate = new Item();
        itemToCreate.setName("Health Potion");
        itemToCreate.setPrice(BigDecimal.valueOf(10.99));
        itemToCreate.setDescription("Restores 50 HP");

        Item createdItem = new Item();
        createdItem.setId(1L);
        createdItem.setName("Health Potion");
        createdItem.setPrice(BigDecimal.valueOf(10.99));
        createdItem.setDescription("Restores 50 HP");

        when(itemService.createItem(any(Item.class))).thenReturn(createdItem);

        // When & Then
        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Health Potion"))
                .andExpect(jsonPath("$.price").value(10.99))
                .andExpect(jsonPath("$.description").value("Restores 50 HP"));
    }

    @Test
    void createItem_shouldReturn400_whenValidationFails() throws Exception {
        // Given
        Item invalidItem = new Item();
        // Name is missing, which is required

        // When & Then
        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidItem)))
                .andExpect(status().isBadRequest());
    }
} 