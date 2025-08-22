package com.jule.studentmanagement.infrastructure.repository;

import com.jule.studentmanagement.application.ports.output.StudentRepositoryPort;
import com.jule.studentmanagement.domain.model.Student;
import com.jule.studentmanagement.infrastructure.dto.PageDTO;
import com.jule.studentmanagement.infrastructure.dto.StudentDTO;
import com.jule.studentmanagement.infrastructure.entity.StudentEntity;
import com.jule.studentmanagement.infrastructure.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class JpaStudentRepositoryAdapter implements StudentRepositoryPort {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public JpaStudentRepositoryAdapter(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public List<Student> getAllStudents() {
        log.debug("Obteniendo todos los estudiantes");
        List<StudentEntity> students = studentRepository.findAll();
        return studentMapper.toDomainList(students);
    }

    @Override
    public PageDTO<StudentDTO> findAllStudentsWithFilters(String name, String email, String countryCode, Boolean active, int page, int size) {
        log.debug("Obteniendo todos los estudiantes con filtros");

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC);
        Page<StudentEntity> pageEntity =  studentRepository.findStudentsWithFilters(name,email,countryCode,active,pageable);
        List<StudentDTO> dtos = pageEntity.getContent().stream()
                .map(studentMapper::toDTO)
                .toList();
        return new PageDTO<>(dtos, pageEntity.getTotalElements(), pageEntity.getTotalPages(), page, size);
    }
}
