package com.jule.studentmanagement;

import com.jule.studentmanagement.domain.model.exception.BusinessException;
import com.jule.studentmanagement.domain.model.exception.ResourceNotFoundException;
import com.jule.studentmanagement.infrastructure.dto.StudentDTO;
import com.jule.studentmanagement.infrastructure.entity.StudentEntity;
import com.jule.studentmanagement.infrastructure.mapper.StudentMapper;
import com.jule.studentmanagement.infrastructure.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final ExternalRestService externalRestService;
    private final ExternalSoapService externalSoapService;

    public List<StudentDTO> getAllStudents() {
        log.debug("Obteniendo todos los estudiantes");
        List<StudentEntity> students = studentRepository.findAll();
        return students.stream()
                .map(this::enrichStudentData)
                .collect(Collectors.toList());
    }

    public Page<StudentDTO> getStudentsWithFilters(String name, String email,
                                                   String countryCode, Boolean active,
                                                   Pageable pageable) {
        log.debug("Obteniendo estudiantes con filtros: name={}, email={}, countryCode={}, active={}",
                name, email, countryCode, active);

        Page<StudentEntity> students = studentRepository.findStudentsWithFilters(
                name, email, countryCode, active, pageable);

        return students.map(this::enrichStudentData);
    }

    public StudentDTO getStudentById(Long id) {
        log.debug("Obteniendo estudiante con ID: {}", id);
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con ID: " + id));

        return enrichStudentData(student);
    }

    public StudentDTO createStudent(StudentDTO studentDTO) {
        log.debug("Creando nuevo estudiante: {}", studentDTO);

        validateStudentEmail(studentDTO.getEmail(), null);

        StudentEntity student = studentMapper.toEntity(studentDTO);
        StudentEntity savedStudent = studentRepository.save(student);

        log.info("Estudiante creado exitosamente con ID: {}", savedStudent.getId());
        return enrichStudentData(savedStudent);
    }

    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        log.debug("Actualizando estudiante con ID: {}", id);

        StudentEntity existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con ID: " + id));

        validateStudentEmail(studentDTO.getEmail(), id);

        studentMapper.updateStudentFromDTO(studentDTO, existingStudent);
        StudentEntity updatedStudent = studentRepository.save(existingStudent);

        log.info("Estudiante actualizado exitosamente con ID: {}", updatedStudent.getId());
        return enrichStudentData(updatedStudent);
    }

    public void deleteStudent(Long id) {
        log.debug("Eliminando estudiante con ID: {}", id);

        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con ID: " + id));

        studentRepository.delete(student);
        log.info("Estudiante eliminado exitosamente con ID: {}", id);
    }

    public void deactivateStudent(Long id) {
        log.debug("Desactivando estudiante con ID: {}", id);

        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con ID: " + id));

        student.setActive(false);
        studentRepository.save(student);

        log.info("Estudiante desactivado exitosamente con ID: {}", id);
    }

    public List<StudentDTO> getActiveStudents() {
        log.debug("Obteniendo estudiantes activos");
        List<StudentEntity> activeStudents = studentRepository.findByActiveTrue();
        return activeStudents.stream()
                .map(this::enrichStudentData)
                .collect(Collectors.toList());
    }

    public List<StudentDTO> searchStudentsByName(String name) {
        log.debug("Buscando estudiantes por nombre: {}", name);
        List<StudentEntity> students = studentRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
        return students.stream()
                .map(this::enrichStudentData)
                .collect(Collectors.toList());
    }

    public long getActiveStudentsCount() {
        return studentRepository.countActiveStudents();
    }

    public List<Object[]> getStudentCountByCountry() {
        return studentRepository.countStudentsByCountry();
    }

    private StudentDTO enrichStudentData(StudentEntity student) {
        StudentDTO studentDTO = studentMapper.toDTO(student);

        // Enriquecer con información del país (servicio SOAP)
        if (student.getCountryCode() != null) {
            try {
                studentDTO.setCountryInfo(
                        externalSoapService.getCountryInfo(student.getCountryCode())
                );
            } catch (Exception e) {
                log.warn("Error al obtener información del país {}: {}",
                        student.getCountryCode(), e.getMessage());
            }
        }

        // Enriquecer con post externo (servicio REST)
        if (student.getExternalPostId() != null) {
            try {
                studentDTO.setExternalPost(
                        externalRestService.getPostById(student.getExternalPostId())
                );
            } catch (Exception e) {
                log.warn("Error al obtener post externo {}: {}",
                        student.getExternalPostId(), e.getMessage());
            }
        }

        return studentDTO;
    }

    private void validateStudentEmail(String email, Long excludeId) {
        boolean emailExists = (excludeId == null)
                ? studentRepository.existsByEmail(email)
                : studentRepository.existsByEmailAndIdNot(email, excludeId);

        if (emailExists) {
            throw new BusinessException("Ya existe un estudiante con el email: " + email);
        }
    }
}