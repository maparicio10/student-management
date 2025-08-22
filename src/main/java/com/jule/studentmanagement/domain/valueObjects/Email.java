package com.jule.studentmanagement.domain.valueObjects;

public record Email(String value) {
    public Email {
        if (value == null) {
            throw new IllegalArgumentException("Email es requerido");
        }
        String v = value.trim();
        if (v.isEmpty()) throw new IllegalArgumentException("Email no puede estar vacío");
        if (!v.matches("[^@]+@[^@]+\\.[^@]+")) {
            throw new IllegalArgumentException("Email inválido: " + v);
        }
        value = v;
    }
}

