package com.jule.studentmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class StudentManagementApplication {

    public static void main(String[] args) {

        SpringApplication.run(StudentManagementApplication.class, args);
        System.out.println("=================================");
        System.out.println("🚀 Student Management API iniciada!");
//        System.out.println("📚 Swagger UI: http://localhost:8080/swagger-ui.html");
//        System.out.println("🔧 H2 Console: http://localhost:8080/h2-console");
        System.out.println("🌐 API Base: http://localhost:8080/api/students");
        System.out.println("=================================");
    }

}
