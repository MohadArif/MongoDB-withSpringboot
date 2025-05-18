package com.example.mongoDB.controller;

import com.example.mongoDB.entity.Employee;
import com.example.mongoDB.service.EmpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emp")
@Tag(name = "Employee_Controller")
public class EmpController {

    private final EmpService empService;

    public EmpController(EmpService empService) {
        this.empService = empService;
    }

    @Operation(summary = "Api for create Employee")
    @PostMapping("/save")
    public ResponseEntity<?> saveEmployee(@RequestBody Employee employee) {
        empService.createEmployee(employee);
        return new ResponseEntity<>("emplyee created !!", HttpStatus.CREATED);
    }

    @Operation(summary = "Api for finding All Employee")
    @GetMapping("/findAll")
    public ResponseEntity<?> findAllEmployee() {
        List<Employee> allEmp = empService.findAllEmp();
        return new ResponseEntity<>(allEmp, HttpStatus.OK);
    }

    @Operation(summary = "Api employee find By id")
    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findEmplById(@PathVariable String id) {
        Employee employeeById = empService.findEmployeeById(id);
        return new ResponseEntity<>(employeeById, HttpStatus.OK);
    }

    @Operation(summary = "Api update Employee")
    @PutMapping("/update")
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee) {
        empService.updateEmplyee(employee);
        return new ResponseEntity<>("updated successfully", HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Api delete Employee by id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmp(@PathVariable String id) {
        empService.deleteEmployee(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @Operation(summary = "api find employee by name")
    @GetMapping("/findByName/{name}")
    public ResponseEntity<?> findEmployeeByName(@PathVariable String name) {
        Employee empByName = empService.findEmpByName(name);
        return new ResponseEntity<>(empByName, HttpStatus.OK);
    }


    @Operation(summary = "Api for finding employee by empcode")
    @GetMapping("/empCode/{empCode}")
    public ResponseEntity<?> findEmployeeByEmpCode(@PathVariable Long empCode) {
        Employee employeeByEmpCode = empService.findEmployeeByEmpCode(empCode);
        return new ResponseEntity<>(employeeByEmpCode, HttpStatus.OK);
    }

    @Operation(summary = "api for findAll employee by their address")
    @GetMapping("/findEmpByCity/{address}")
    public ResponseEntity<?> getEmployeeBasedOnAddress(@PathVariable String address) {
        List<Employee> allEmployeeBasedOnCity = empService.findAllEmployeeBasedOnCity(address);
        return new ResponseEntity<>(allEmployeeBasedOnCity, HttpStatus.OK);
    }


    @Operation(summary = "api for findAll Employee with sorting and pagination")
    @GetMapping("/GetAllSortedEmployee")
    public ResponseEntity<?> getAllEmployeeByWithSortingOrder(@RequestParam(defaultValue = "0") int pageNo,
                                                              @RequestParam(required = false) String sort,
                                                              @RequestParam(defaultValue = "5") int pageSize
    ) {
        Page<Employee> sortedEmplpyee = empService.getSortedEmplpyee(pageNo, sort, pageSize);
        return new ResponseEntity<>(sortedEmplpyee, HttpStatus.OK);
    }

    @Operation(summary = "Api to find employee between empCode")
    @GetMapping("/getEmpBetween/{minEmpCode}/{maxEmpCode}")
    public ResponseEntity<?> getAllEmployeeEmpCodeBetween(@PathVariable Long minEmpCode,@PathVariable Long maxEmpCode){
        List<Employee> employeeEmplCodeBetween = empService.getEmployeeEmplCodeBetween(minEmpCode,maxEmpCode);
        return new ResponseEntity<>(employeeEmplCodeBetween,HttpStatus.OK);
    }
}