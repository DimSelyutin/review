package com.dmitrij.restapi.controller;

import com.dmitrij.restapi.DTO.EmployeeDTO;
import com.dmitrij.restapi.entity.Employee;
import com.dmitrij.restapi.repository.EmployeeRepo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@Tag(name = "main rest methods")
@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController {

    private final EmployeeRepo employeeRepo;

    @PostMapping("/api/add")
    public void addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = Employee.builder().name(employeeDTO.getName())
                .age(employeeDTO.getAge())
                .salary(employeeDTO.getSalary())
                .build();
        try {
            log.info("new row: " + employeeRepo.save(employee));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "entity already exists"
            );
        }
    }

    @SneakyThrows
    @GetMapping("/api/all")
    public List<Employee> getAll() {
        return employeeRepo.findAll();
    }

    @GetMapping("/api")
    public Employee findEmployeeById(@RequestParam int id) {
        try {
            return employeeRepo.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
    }

    @DeleteMapping("/api")
    public void deleteEmployee(@RequestParam int id) {
        employeeRepo.deleteById(id);
    }

    @PutMapping("/api")
    public String changeEmployeeData(@RequestBody Employee employee) {
        if (!employeeRepo.existsById(employee.getId())) {
            log.info("Employee doesn't exist");
            return "Employee doesn't exist";
        }
        return employeeRepo.save(employee).toString();
    }

}
