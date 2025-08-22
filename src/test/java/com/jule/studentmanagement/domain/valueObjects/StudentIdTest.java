package com.jule.studentmanagement.domain.valueObjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentIdTest {

    @Test
    void shouldCreateValidStudentId() {
        StudentId id = new StudentId(1L);
        assertNotNull(id);
        assertEquals(1L, id.value());
    }

    @Test
    void shouldThrowExceptionWhenIdIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new StudentId(null));
        assertEquals("StudentId no puede ser null", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenIdIsZero() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new StudentId(0L));
        assertEquals("StudentId debe ser positivo", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenIdIsNegative() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new StudentId(-5L));
        assertEquals("StudentId debe ser positivo", ex.getMessage());
    }

    @Test
    void shouldConsiderTwoStudentIdsWithSameValueAsEqual() {
        StudentId id1 = new StudentId(10L);
        StudentId id2 = new StudentId(10L);

        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    void shouldCreateFromFactoryMethods() {
        StudentId fromLong = StudentId.from(5L);
        assertEquals(5L, fromLong.value());

        StudentId fromString = StudentId.of("12");
        assertEquals(12L, fromString.value());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> StudentId.of("abc"));
        assertTrue(ex.getMessage().contains("StudentId inválido"));
    }
}

