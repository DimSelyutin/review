package com.dmitrij.restapi.DTO;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeDTO {
    String name;
    int age;
    int salary;
}
