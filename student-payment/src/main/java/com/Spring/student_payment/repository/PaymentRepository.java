package com.Spring.student_payment.repository;

import com.Spring.student_payment.entities.Payment;
import com.Spring.student_payment.entities.PaymentStatus;
import com.Spring.student_payment.entities.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByStudentCode(String code);

    List<Payment> findByStatus(PaymentStatus status);

    List<Payment> findByType(PaymentType type);
}
