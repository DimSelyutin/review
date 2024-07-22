package com.dmitrij.restapi.controller;

import com.dmitrij.restapi.entity.Employee;
import com.dmitrij.restapi.kafka.KafkaProducer;
import com.dmitrij.restapi.repository.EmployeeRepo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class KafkaController {

    private final KafkaProducer kafkaProducer;
    private final EmployeeRepo employeeRepo;

    public KafkaController(KafkaProducer kafkaProducer, EmployeeRepo employeeRepo) {
        this.kafkaProducer = kafkaProducer;
        this.employeeRepo = employeeRepo;
    }

    @PostMapping("kafka/send")
    public String sendMessage(@RequestParam int id) {
    Optional<Employee> employee = employeeRepo.findById(id);
        kafkaProducer.send(employee.toString());
        return "success";
    }
}
