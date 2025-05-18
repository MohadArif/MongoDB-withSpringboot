package com.example.mongoDB.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class CustomExceptionHandler{

    @ExceptionHandler(value = EmployeeNotFoundException.class)
    public ResponseEntity<ExceptionResponse> employeeNotFoundException(){
        ExceptionResponse exceptionResponse = new ExceptionResponse("sorry employee doesn't exist..", 404, new Date());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);

    }


}
