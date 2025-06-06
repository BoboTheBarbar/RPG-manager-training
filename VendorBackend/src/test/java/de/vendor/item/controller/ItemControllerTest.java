package de.vendor.item.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.vendor.item.adapter.ItemAdapter;
import de.vendor.item.api.ItemController;
import de.vendor.item.domain.DomainItem;
import de.vendor.item.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
@Import(ItemAdapter.class)  // Importiere die echte ItemAdapter Klasse
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemService itemService;
    
    // ItemAdapter wird automatisch als echte Bean injiziert

    private DomainItem item;

    @BeforeEach
    void setUp() {
        item = new DomainItem();
        item.setId(1L);
        item.setName("Health Potion");
        item.setDescription("Restores 50 HP");
        item.setPrice(10.99);
    }

    @Test
    void getAllItems_ShouldReturnItems() throws Exception {
        DomainItem secondItem = new DomainItem();
        secondItem.setId(2L);
        secondItem.setName("Mana Potion");
        secondItem.setDescription("Restores 50 MP");
        secondItem.setPrice(10.99);

        when(itemService.getAllItems()).thenReturn(Arrays.asList(item, secondItem));

        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Health Potion"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Mana Potion"));
    }

    @Test
    void getItemById_WithValidId_ShouldReturnItem() throws Exception {
        when(itemService.getItemById(1L)).thenReturn(Optional.of(item));

        mockMvc.perform(get("/api/items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Health Potion"));
    }

    @Test
    void getItemById_WithInvalidId_ShouldReturn404() throws Exception {
        when(itemService.getItemById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/items/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createItem_WithValidData_ShouldReturnCreatedItem() throws Exception {
        de.vendor.item.model.ItemCreate itemToCreate = new de.vendor.item.model.ItemCreate()
                .name("Health Potion")
                .description("Restores 50 HP")
                .price(10.99);

        DomainItem createdItem = new DomainItem();
        createdItem.setId(1L);
        createdItem.setName("Health Potion");
        createdItem.setDescription("Restores 50 HP");
        createdItem.setPrice(10.99);

        when(itemService.createItem(any(DomainItem.class))).thenReturn(createdItem);

        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Health Potion"));
    }
}
