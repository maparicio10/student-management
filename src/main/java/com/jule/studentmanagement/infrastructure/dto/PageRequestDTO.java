package com.jule.studentmanagement.infrastructure.dto;

public record PageRequestDTO(int page, int size) {
    public PageRequestDTO {
        if (page < 0) throw new IllegalArgumentException("Page must be >= 0");
        if (size <= 0) throw new IllegalArgumentException("Size must be > 0");
    }
}