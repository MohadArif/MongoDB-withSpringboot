package com.example.mongoDB.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class EmployeeNotFoundException extends RuntimeException{


    public EmployeeNotFoundException(String message) {
        super(message);
    }

}
