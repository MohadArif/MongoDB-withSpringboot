package com.example.mongoDB.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "employee")
public class Employee {
    @Id
    private String id;
    private String empName;
    private Long empCode;
    private String address;
}
