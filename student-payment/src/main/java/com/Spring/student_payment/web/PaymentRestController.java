package com.Spring.student_payment.web;

import com.Spring.student_payment.dtos.NewPaymentDTO;
import com.Spring.student_payment.entities.Payment;
import com.Spring.student_payment.entities.PaymentStatus;
import com.Spring.student_payment.entities.PaymentType;
import com.Spring.student_payment.entities.Student;
import com.Spring.student_payment.repository.PaymentRepository;
import com.Spring.student_payment.repository.StudentRepository;
import com.Spring.student_payment.services.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
public class PaymentRestController {

    private StudentRepository studentRepository;

    private PaymentRepository paymentRepository;
    private PaymentService paymentService;

    public PaymentRestController(StudentRepository studentRepository, PaymentRepository paymentRepository, PaymentService paymentService) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
    }

    @GetMapping(path = "/payments")
    public List<Payment> allPayment(){
        return paymentRepository.findAll();
    }
    @GetMapping(path = "/payments/byType")
    public List<Payment> paymentsByType(@RequestParam PaymentType Type){
        return paymentRepository.findByType(Type);
    }
    @GetMapping(path = "/payments/byStatus")
    public List<Payment> paymentsByStatus(@RequestParam PaymentStatus status){
        return paymentRepository.findByStatus(status);
    }
    @GetMapping(path = "/students/{code}/payments")
    public List<Payment> paymentsBySudent(@PathVariable String code){
        return paymentRepository.findByStudentCode(code);
    }
    @GetMapping(path = "/payments/{id}")
    public Payment getPaymentById(@PathVariable  Long id){
        return paymentRepository.findById(id).get();
    }
    @GetMapping(path = "/students")
    public List<Student> allStudents(){
        return studentRepository.findAll();
    }
    @GetMapping(path = "/students/{code}")
    public Student getStudentByCode(@PathVariable String code){
        return studentRepository.findByCode(code);
    }
    @GetMapping(path = "/studentsByProgramId")
    public List<Student> getStudentsByProgramId(@RequestParam String programId){
       return studentRepository.findByProgramId(programId);
    }
    @PutMapping("/payments/{id}")
    public Payment updatePaymentStatus(@RequestParam PaymentStatus status, @PathVariable Long id){
        return  paymentService.updatePaymentStatus(status, id);
    }
    @PostMapping(path = "/payments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment savePayment(@RequestParam("file")  MultipartFile file, NewPaymentDTO newPaymentDTO) throws IOException {

        return this.paymentService.savePayment(file, newPaymentDTO);
    }
    @GetMapping(path = "/paymentFile/{paymentId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile(@PathVariable  Long paymentId) throws IOException {
        return paymentService.getPaymentFile(paymentId);
    }
}
