package com.Spring.student_payment.services;

import com.Spring.student_payment.dtos.NewPaymentDTO;
import com.Spring.student_payment.entities.Payment;
import com.Spring.student_payment.entities.PaymentStatus;
import com.Spring.student_payment.entities.PaymentType;
import com.Spring.student_payment.entities.Student;
import com.Spring.student_payment.repository.PaymentRepository;
import com.Spring.student_payment.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {
    private StudentRepository studentRepository;

    private PaymentRepository paymentRepository;

    public PaymentService(StudentRepository studentRepository, PaymentRepository paymentRepository){
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }

    public Payment savePayment(MultipartFile file, NewPaymentDTO newPaymentDTO) throws IOException {
        Path folderPath = Paths.get(System.getProperty("user.home"),"enset-data","payments");
        if(!Files.exists(folderPath)){
            Files.createDirectories(folderPath);
        }
        String fileName = UUID.randomUUID().toString();
        Path filePath = Paths.get(System.getProperty("user.home"),"enset-data","payments",fileName+".pdf");
        Files.copy(file.getInputStream(),filePath);
        Student student = studentRepository.findByCode(newPaymentDTO.getStudentCode());
        if (student == null) {
            throw new EntityNotFoundException("Student not found with code: " + newPaymentDTO.getStudentCode());
        }

        Payment payment = Payment.builder()
                .type(newPaymentDTO.getType()).
                status(PaymentStatus.CREATED)
                .date(newPaymentDTO.getDate()).
                student(student)
                .amount(newPaymentDTO.getAmount()).file(filePath.toUri().toString())
                .build();

        return  paymentRepository.save(payment);
    }


    public Payment updatePaymentStatus( PaymentStatus status, Long id){
        Payment payment = paymentRepository.findById(id).get();
        payment.setStatus(status);
        return  paymentRepository.save(payment);
    }


    public byte[] getPaymentFile( Long paymentId) throws IOException {
        Payment payment = paymentRepository.findById(paymentId).get();
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }
}
