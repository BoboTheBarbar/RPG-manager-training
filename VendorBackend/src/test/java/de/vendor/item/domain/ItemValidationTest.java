package de.vendor.item.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ItemValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void createValidItem_shouldPassValidation() {
        // Given
        Item item = new Item();
        item.setName("Health Potion");
        item.setPrice(BigDecimal.valueOf(10.99));
        item.setDescription("Restores 50 HP");

        // When
        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void createItemWithNullName_shouldFailValidation() {
        // Given
        Item item = new Item();
        item.setPrice(BigDecimal.valueOf(10.99));
        item.setDescription("Restores 50 HP");

        // When
        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("name");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void createItemWithBlankName_shouldFailValidation(String blankName) {
        // Given
        Item item = new Item();
        item.setName(blankName);
        item.setPrice(BigDecimal.valueOf(10.99));
        item.setDescription("Restores 50 HP");

        // When
        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("name");
    }

    @Test
    void createItemWithNullPrice_shouldFailValidation() {
        // Given
        Item item = new Item();
        item.setName("Health Potion");
        item.setDescription("Restores 50 HP");

        // When
        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("price");
    }

    @Test
    void createItemWithNegativePrice_shouldFailValidation() {
        // Given
        Item item = new Item();
        item.setName("Health Potion");
        item.setPrice(BigDecimal.valueOf(-10.99));
        item.setDescription("Restores 50 HP");

        // When
        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("price");
    }

    @Test
    void createItemWithNullDescription_shouldPassValidation() {
        // Given
        Item item = new Item();
        item.setName("Health Potion");
        item.setPrice(BigDecimal.valueOf(10.99));

        // When
        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        // Then
        assertThat(violations).isEmpty();
    }
} 