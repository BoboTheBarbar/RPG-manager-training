package de.vendor.item.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ItemValidationTest {

    private Validator validator;
    private Item item;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        item = new Item();
    }

    @Test
    void whenAllFieldsAreValid_thenNoViolations() {
        item.setName("Health Potion");
        item.setDescription("Restores 50 HP");
        item.setPrice(10.99);

        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenNameIsNull_thenViolation() {
        item.setDescription("Restores 50 HP");
        item.setPrice(10.99);

        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        assertThat(violations)
            .hasSize(1)
            .extracting(violation -> violation.getPropertyPath().toString())
            .containsExactly("name");
    }

    @Test
    void whenPriceIsNull_thenViolation() {
        item.setName("Health Potion");
        item.setDescription("Restores 50 HP");

        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        assertThat(violations)
            .hasSize(1)
            .extracting(violation -> violation.getPropertyPath().toString())
            .containsExactly("price");
    }

    @Test
    void whenPriceIsNegative_thenViolation() {
        item.setName("Health Potion");
        item.setDescription("Restores 50 HP");
        item.setPrice(-10.99);

        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        assertThat(violations)
            .hasSize(1)
            .extracting(violation -> violation.getPropertyPath().toString())
            .containsExactly("price");
    }

    @Test
    void whenDescriptionIsOptional_thenNoViolations() {
        item.setName("Health Potion");
        item.setPrice(10.99);

        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void createItemWithBlankName_shouldFailValidation(String blankName) {
        // Given
        item.setName(blankName);
        item.setPrice(10.99);
        item.setDescription("Restores 50 HP");

        // When
        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        // Then
        assertThat(violations)
            .hasSize(1)
            .extracting(violation -> violation.getPropertyPath().toString())
            .containsExactly("name");
    }
} 