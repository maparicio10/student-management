package com.jule.studentmanagement.domain.valueObjects;

public record StudentId(Long value) {

    public StudentId {
        if (value == null) {
            throw new IllegalArgumentException("StudentId no puede ser null");
        }
        if (value <= 0) {
            throw new IllegalArgumentException("StudentId debe ser positivo");
        }
    }

    public static StudentId from(Long value) {
        return new StudentId(value);
    }

    public static StudentId of(String value) {
        try {
            return new StudentId(Long.parseLong(value));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("StudentId inválido: " + value, e);
        }
    }
}

