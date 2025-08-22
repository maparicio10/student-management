package com.jule.studentmanagement.application.ports.input;

import com.jule.studentmanagement.infrastructure.dto.StudentDTO;

import java.util.List;

public interface GetAllStudentsUseCase {
    List<StudentDTO> getAllStudents();
}
