package com.jule.studentmanagement.domain.valueObjects;


import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateOfBirthTest {

    @Test
    void shouldCreateValidDateOfBirth() {
        LocalDate birthDate = LocalDate.now().minusYears(20);
        DateOfBirth dob = new DateOfBirth(birthDate);

        assertNotNull(dob);
        assertEquals(20, dob.age());
        assertEquals(birthDate, dob.value());
    }

    @Test
    void shouldThrowExceptionWhenDateIsNull() {
        Exception ex = assertThrows(NullPointerException.class, () -> new DateOfBirth(null));
        assertEquals("DateOfBirth no puede ser null", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDateIsInTheFuture() {
        LocalDate futureDate = LocalDate.now().plusDays(1);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new DateOfBirth(futureDate));
        assertEquals("DateOfBirth no puede estar en el futuro", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAgeIsTooYoung() {
        LocalDate tooYoung = LocalDate.now().minusYears(2);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new DateOfBirth(tooYoung));
        assertTrue(ex.getMessage().contains("La edad del estudiante debe estar entre 3 y 100 años"));
    }

    @Test
    void shouldThrowExceptionWhenAgeIsTooOld() {
        LocalDate tooOld = LocalDate.now().minusYears(101);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new DateOfBirth(tooOld));
        assertTrue(ex.getMessage().contains("La edad del estudiante debe estar entre 3 y 100 años"));
    }
}

