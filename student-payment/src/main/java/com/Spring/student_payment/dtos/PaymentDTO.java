package com.Spring.student_payment.dtos;

import com.Spring.student_payment.entities.PaymentStatus;
import com.Spring.student_payment.entities.PaymentType;
import com.Spring.student_payment.entities.Student;
import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
public class PaymentDTO {

    private Long id;

    private LocalDate date;

    private double amount;

    private PaymentType type;

    private PaymentStatus status;

    private String file;

    private Student student;
}
