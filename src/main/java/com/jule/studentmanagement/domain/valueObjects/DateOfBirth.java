package com.jule.studentmanagement.domain.valueObjects;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public record DateOfBirth(LocalDate value) {

    public DateOfBirth {
        Objects.requireNonNull(value, "DateOfBirth no puede ser null");

        if (value.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("DateOfBirth no puede estar en el futuro");
        }

        int age = Period.between(value, LocalDate.now()).getYears();
        if (age < 3 || age > 100) {
            throw new IllegalArgumentException("La edad del estudiante debe estar entre 3 y 100 años. Edad actual: " + age);
        }
    }

    public int age() {
        return Period.between(value, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
