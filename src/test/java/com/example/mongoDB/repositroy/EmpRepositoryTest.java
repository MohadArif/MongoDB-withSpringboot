package com.example.mongoDB.repositroy;

import com.example.mongoDB.entity.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class EmpRepositoryTest {

    @Autowired
    private EmpRepository empRepository;
    Employee employee;   //local variable to store data.

    @BeforeEach
    void setUp() {
        employee = new Employee("xxxxxxx", "Rahul kumar",
                121L, "bihar");  //Saves the employee to the in-memory test MongoDB
        empRepository.save(employee);
    }

    @AfterEach
    void tearDown() {
        employee = null;       //closing the resources by deleting the emp data from local variable.
        empRepository.deleteAll();

    }

    @Test
    void testGetEmployeeByName() {
        Employee employeeByName = empRepository.getEmployeeByName("Rahul kumar");  //custom repository method
        org.assertj.core.api.Assertions.assertThat(employeeByName.getEmpName())
                .isEqualTo(employee.getEmpName());


    }

    @Test
    void getEmployeeByEmpCode() {
        Employee byEmpCode = empRepository.getEmployeeByEmpCode(121L).get();
        org.assertj.core.api.Assertions.assertThat(byEmpCode.getEmpCode())
                .isEqualTo(employee.getEmpCode());

        org.assertj.core.api.Assertions.assertThat(byEmpCode.getEmpName())
                .isEqualTo("Rahul kumar");

    }


}