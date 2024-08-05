package com.Spring.student_payment.repository;

import com.Spring.student_payment.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {

    Student findByCode(String code);

    List<Student> findByProgramId(String programId);
}
