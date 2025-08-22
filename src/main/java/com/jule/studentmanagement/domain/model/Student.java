package com.jule.studentmanagement.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Student {
    private final Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private String phone;
    private String address;
    private String countryCode;
    private Long externalPostId;
    private final boolean active;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor privado
    private Student(Long id,
                    String firstName,
                    String lastName,
                    LocalDate birthDate,
                    String phone,
                    String address,
                    boolean active,
                    LocalDateTime createdAt,
                    LocalDateTime updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.phone = phone;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Factory Method para crear nuevo Student
    public static Student create(String firstName,
                                 String lastName,
                                 LocalDate birthDate,
                                 String phone,
                                 String address,
                                 boolean active) {
        LocalDateTime now = LocalDateTime.now();
        return new Student(null, firstName, lastName, birthDate, phone, address, active, now, now);
    }

    // Reconstrucción desde persistencia
    public static Student fromPersistence(Long id,
                                          String firstName,
                                          String lastName,
                                          LocalDate birthDate,
                                          String phone,
                                          String address,
                                          boolean active,
                                          LocalDateTime createdAt,
                                          LocalDateTime updatedAt) {
        return new Student(id, firstName, lastName, birthDate, phone, address, active, createdAt, updatedAt);
    }

    private void markUpdated() {
        this.updatedAt = LocalDateTime.now();
    }

    public void updateDetails(String firstName,
                              String lastName,
                              LocalDate birthDate,
                              String phone,
                              String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.address = address;
        this.updatedAt = LocalDateTime.now();
        markUpdated();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Long getExternalPostId() {
        return externalPostId;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                '}';
    }
}
