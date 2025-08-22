package com.jule.studentmanagement.infrastructure.dto;

import java.util.List;

public record PageDTO<T>(List<T> content, long totalElements, int totalPages, int page, int size) {
}
