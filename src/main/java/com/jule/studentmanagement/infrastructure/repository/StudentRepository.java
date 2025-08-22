package com.jule.studentmanagement.infrastructure.repository;

import com.jule.studentmanagement.infrastructure.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    Optional<StudentEntity> findByEmail(String email);

    List<StudentEntity> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName, String lastName);

    List<StudentEntity> findByActiveTrue();

    List<StudentEntity> findByCountryCode(String countryCode);

    @Query("SELECT s FROM StudentEntity s WHERE s.birthDate BETWEEN :startDate AND :endDate")
    List<StudentEntity> findByBirthDateBetween(@Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate);

    @Query("SELECT s FROM StudentEntity s WHERE " +
            "(:name IS NULL OR LOWER(s.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
            "LOWER(s.lastName) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:email IS NULL OR LOWER(s.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
            "(:countryCode IS NULL OR s.countryCode = :countryCode) AND " +
            "(:active IS NULL OR s.active = :active)")
    Page<StudentEntity> findStudentsWithFilters(@Param("name") String name,
                                                @Param("email") String email,
                                                @Param("countryCode") String countryCode,
                                                @Param("active") Boolean active,
                                                Pageable pageable);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    @Query("SELECT COUNT(s) FROM StudentEntity s WHERE s.active = true")
    long countActiveStudents();

    @Query("SELECT s.countryCode, COUNT(s) FROM StudentEntity s GROUP BY s.countryCode")
    List<Object[]> countStudentsByCountry();
}
