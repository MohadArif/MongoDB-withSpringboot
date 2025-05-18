package com.example.mongoDB.service;

import com.example.mongoDB.entity.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmpService {


    void createEmployee(Employee employee);

    List<Employee> findAllEmp();

    Employee findEmployeeById(String id);

    void updateEmplyee(Employee employee);

    void deleteEmployee(String id);

    Employee findEmpByName(String name);

    Employee findEmployeeByEmpCode(Long empCode);

    List<Employee> findAllEmployeeBasedOnCity(String address);

    Page<Employee> getSortedEmplpyee(int pageNo, String sort, int pageSize);

    List<Employee> getEmployeeEmplCodeBetween(Long minEmpCode, Long maxEmpCode);
}
