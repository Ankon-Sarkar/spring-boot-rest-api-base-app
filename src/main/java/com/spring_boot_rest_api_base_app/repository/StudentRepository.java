package com.spring_boot_rest_api_base_app.repository;

import com.spring_boot_rest_api_base_app.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
