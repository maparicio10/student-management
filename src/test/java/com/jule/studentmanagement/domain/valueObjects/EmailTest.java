package com.jule.studentmanagement.domain.valueObjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void shouldCreateValidEmail() {
        Email email = new Email("test@example.com");
        assertNotNull(email);
        assertEquals("test@example.com", email.value());
    }

    @Test
    void shouldTrimEmail() {
        Email email = new Email("  user@domain.com  ");
        assertEquals("user@domain.com", email.value());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Email(null)
        );
        assertEquals("Email es requerido", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsEmpty() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Email("   ")
        );
        assertEquals("Email no puede estar vacío", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidEmailFormat() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new Email("invalid-email")
        );
        assertEquals("Email inválido: invalid-email", ex.getMessage());
    }
}

