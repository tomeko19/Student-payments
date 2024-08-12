package com.Spring.student_payment;

import com.Spring.student_payment.entities.Payment;
import com.Spring.student_payment.entities.PaymentStatus;
import com.Spring.student_payment.entities.PaymentType;
import com.Spring.student_payment.entities.Student;
import com.Spring.student_payment.repository.PaymentRepository;
import com.Spring.student_payment.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class StudentPaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentPaymentApplication.class, args);
	}
     @Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository, PaymentRepository paymentRepository){
		return  args -> {
			studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
					.firstName("Tchato").code("112233").programId("SDIA").build());

			studentRepository.save(Student.builder().id(UUID.randomUUID().toString()).firstName("Tomeko")
					.code("112244").programId("SDIA").build());

			studentRepository.save(Student.builder().id(UUID.randomUUID().toString()).firstName("Ghislain")
					.code("112255").programId("GLSID").build());

			studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
					.firstName("Chadric").code("112266").programId("BDCC").build());

			 PaymentType[] paymentTypes = PaymentType.values();
			Random random = new Random();
			studentRepository.findAll().forEach(st->{
				for (int i =0; i<10; i++){
					int index =  random.nextInt(paymentTypes.length);
					Payment payment = Payment.builder().amount(1000+(int)(Math.random()*2000))
							.type(paymentTypes[index])
							.status(PaymentStatus.CREATED)
							.date(LocalDate.now())
							.student(st)
							.build();
					paymentRepository.save(payment);
				}
			});
		};
	 }
}
