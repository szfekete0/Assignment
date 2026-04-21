package com.feketsz.assignment.model.converter;

import com.feketsz.assignment.model.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StatusConverterTest {

    private final StatusConverter converter = new StatusConverter();

    @Test
    void convertToDatabaseColumnReturnsCode() {
        assertEquals(4, converter.convertToDatabaseColumn(Status.COMPLETED));
    }

    @Test
    void convertToDatabaseColumnReturnsNullForNullStatus() {
        assertNull(converter.convertToDatabaseColumn(null));
    }

    @Test
    void convertToEntityAttributeReturnsEnum() {
        assertEquals(Status.FILTERED, converter.convertToEntityAttribute(3));
    }

    @Test
    void convertToEntityAttributeReturnsNullForNullCode() {
        assertNull(converter.convertToEntityAttribute(null));
    }

    @Test
    void convertToEntityAttributeThrowsForUnknownCode() {
        assertThrows(IllegalArgumentException.class, () -> converter.convertToEntityAttribute(99));
    }
}
