package com.jule.studentmanagement.application.ports.output;


import com.jule.studentmanagement.domain.model.Student;
import com.jule.studentmanagement.infrastructure.dto.PageDTO;
import com.jule.studentmanagement.infrastructure.dto.StudentDTO;

import java.util.List;

public interface StudentRepositoryPort {
    List<Student> getAllStudents();

    PageDTO<StudentDTO> findAllStudentsWithFilters(String name, String email, String countryCode, Boolean active, int page, int size);

}
