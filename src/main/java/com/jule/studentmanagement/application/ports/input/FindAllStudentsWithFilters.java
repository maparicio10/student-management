package com.jule.studentmanagement.application.ports.input;

import com.jule.studentmanagement.infrastructure.dto.PageDTO;
import com.jule.studentmanagement.infrastructure.dto.StudentDTO;

public interface FindAllStudentsWithFilters {
    PageDTO<StudentDTO> findAllStudentsWithFilters(String firstName, String lastName, String email, int page, int size);
}
