package com.jule.studentmanagement.domain.model;

import com.jule.studentmanagement.domain.valueObjects.StudentId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class StudentTest {

    @Test
    void shouldCreateNewStudentWithFactory() {
        LocalDate birthDate = LocalDate.of(2000, 1, 1);
        Student student = Student.create("Juan", "Perez", birthDate, "555-1234", "Calle Falsa 123", true);

        assertThat(student.getFirstName()).isEqualTo("Juan");
        assertThat(student.getLastName()).isEqualTo("Perez");
        assertThat(student.getBirthDate()).isEqualTo(birthDate);
        assertThat(student.getPhone()).isEqualTo("555-1234");
        assertThat(student.getAddress()).isEqualTo("Calle Falsa 123");
        assertThat(student.isActive()).isTrue();
        assertThat(student.getCreatedAt()).isNotNull();
        assertThat(student.getUpdatedAt()).isNotNull();
        assertThat(student.getUpdatedAt()).isEqualTo(student.getCreatedAt());
    }

    @Test
    void shouldReconstructStudentFromPersistence() {
        LocalDate birthDate = LocalDate.of(1995, 5, 10);
        LocalDateTime created = LocalDateTime.now().minusDays(1);
        LocalDateTime updated = LocalDateTime.now();

        Student student = Student.fromPersistence(
                StudentId.from(1L), "Ana", "Gomez", birthDate, "555-5678", "Av Siempre Viva 742", true, created, updated
        );

        assertThat(student.getFirstName()).isEqualTo("Ana");
        assertThat(student.getLastName()).isEqualTo("Gomez");
        assertThat(student.getBirthDate()).isEqualTo(birthDate);
        assertThat(student.getPhone()).isEqualTo("555-5678");
        assertThat(student.getAddress()).isEqualTo("Av Siempre Viva 742");
        assertThat(student.getCreatedAt()).isEqualTo(created);
        assertThat(student.getUpdatedAt()).isEqualTo(updated);
    }

    @Test
    void shouldUpdateStudentDetails() throws InterruptedException {
        LocalDate birthDate = LocalDate.of(2000, 1, 1);
        Student student = Student.create("Juan", "Perez", birthDate, "555-1234", "Calle Falsa 123", true);

        LocalDate newBirthDate = LocalDate.of(2001, 2, 2);
        Thread.sleep(10); // Pequeña espera para que updatedAt cambie
        student.updateDetails("Carlos", "Lopez", newBirthDate, "555-9876", "Nueva Dirección 456");

        assertThat(student.getFirstName()).isEqualTo("Carlos");
        assertThat(student.getLastName()).isEqualTo("Lopez");
        assertThat(student.getBirthDate()).isEqualTo(newBirthDate);
        assertThat(student.getPhone()).isEqualTo("555-9876");
        assertThat(student.getAddress()).isEqualTo("Nueva Dirección 456");
        assertThat(student.getUpdatedAt()).isAfter(student.getCreatedAt());
    }

    @Test
    void shouldRespectEqualityBasedOnId() {
        LocalDate birthDate = LocalDate.of(2000, 1, 1);
        Student s1 = Student.fromPersistence(StudentId.from(1L), "Juan", "Perez", birthDate, "555", "addr", true, LocalDateTime.now(), LocalDateTime.now());
        Student s2 = Student.fromPersistence(StudentId.from(1L), "Ana", "Gomez", birthDate, "555", "addr", true, LocalDateTime.now(), LocalDateTime.now());
        Student s3 = Student.fromPersistence(StudentId.from(2L), "Ana", "Gomez", birthDate, "555", "addr", true, LocalDateTime.now(), LocalDateTime.now());

        assertThat(s1).isEqualTo(s2);
        assertThat(s1).isNotEqualTo(s3);
    }

    @Test
    void toStringShouldContainMainInfo() {
        LocalDate birthDate = LocalDate.of(2000, 1, 1);
        Student student = Student.create("Juan", "Perez", birthDate, "555-1234", "Calle Falsa 123", true);

        String str = student.toString();
        assertThat(str).contains("Juan").contains("Perez").contains("active=true");
    }
}
