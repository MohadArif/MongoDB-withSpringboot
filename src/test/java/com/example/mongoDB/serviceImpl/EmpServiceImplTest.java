package com.example.mongoDB.serviceImpl;

import com.example.mongoDB.entity.Employee;
import com.example.mongoDB.repositroy.EmpRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpServiceImplTest {

    @Mock
    private EmpRepository empRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private EmpServiceImpl empService;

    Employee employee;

    @BeforeEach
    void setUp() {
        employee=new Employee("xxxx","kunal",111L,"gaya");
    }

    @AfterEach
    void tearDown() {
        employee=null;
    }

    @Test
    void createEmployee() {

        when(empRepository.save(employee)).thenReturn(employee);
//      doNothing().when(empRepository).save(employee);  //when method return void the use this.
        empService.createEmployee(employee);   //this method return void
        verify(empRepository,times(1)).save(employee);  // for  verifying that method called once.

    }

    @Test
    void findAllEmp() {
        List<Employee> employees=List.of(new Employee("xxxx","kunal",111L,"gaya"),
                new Employee("xxxx","rahul",112L,"gaya"));
        when(empRepository.findAll()).thenReturn(employees);
        List<Employee> allEmp = empService.findAllEmp();

        assertNotNull(allEmp);
        assertEquals("kunal",allEmp.get(0).getEmpName());
        assertEquals("rahul",allEmp.get(1).getEmpName());
    }

    @Test
    void findEmployeeById() {
        when(empRepository.findById("xxxx")).thenReturn(Optional.ofNullable(employee));
        Employee employeeById = empService.findEmployeeById("xxxx");
        assertEquals("kunal",employeeById.getEmpName());
    }

    @Test
    void updateEmplyee() {
        // Arrange
        Employee existingEmployee = new Employee("1", "Old Name",
                111L, "Old Address");

        when(empRepository.findById("1")).thenReturn(Optional.of(existingEmployee));
        when(empRepository.save(any(Employee.class))).thenReturn(existingEmployee);

        // New data for update
        Employee updatedData = new Employee("1", "New Name", 112L, "New Address");

        // Act
        empService.updateEmplyee(updatedData);

        // Assert
        assertEquals("New Name", existingEmployee.getEmpName());
        assertEquals(112L, existingEmployee.getEmpCode());
        assertEquals("New Address", existingEmployee.getAddress());

        verify(empRepository, times(1)).findById("1");
        verify(empRepository, times(1)).save(existingEmployee);

    }

    @Test
    void deleteEmployee() {
        when(empRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        doNothing().when(empRepository).deleteById(employee.getId());
        empService.deleteEmployee(employee.getId());
        verify(empRepository,times(1)).deleteById(employee.getId());

    }

    @Test
    void findEmpByName() {
        when(empRepository.getEmployeeByName(employee.getEmpName())).thenReturn(employee);
        Employee empByName = empService.findEmpByName(employee.getEmpName());
        assertEquals(employee.getEmpName(),empByName.getEmpName());
        verify(empRepository,times(1)).getEmployeeByName(employee.getEmpName());
    }

    @Test
    void findEmployeeByEmpCode() {
        when(empRepository.getEmployeeByEmpCode(employee.getEmpCode()))
                .thenReturn(Optional.of(employee));
        Employee employeeByEmpCode = empService.findEmployeeByEmpCode(employee.getEmpCode());
        assertEquals(employeeByEmpCode.getEmpName(),employee.getEmpName());
        assertEquals(employeeByEmpCode.getAddress(),employee.getAddress());

    }

    @Test
    void findAllEmployeeBasedOnCity() {
        List<Employee> employees=List.of(new Employee("xxxx","kunal",111L,"gaya"),
                new Employee("xxxx","rahul",112L,"gaya"));
        when(mongoTemplate.find(any(Query.class), eq(Employee.class))).thenReturn(employees);
        List<Employee> allEmployeeBasedOnCity = empService.findAllEmployeeBasedOnCity("gaya");

        assertNotNull(allEmployeeBasedOnCity);
        assertEquals(2, allEmployeeBasedOnCity.size());
        assertEquals("kunal", allEmployeeBasedOnCity.get(0).getEmpName());
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(Employee.class));
    }

    @Test
    void getSortedEmplpyee() {
        int pageNo = 0;
        int pageSize = 2;
        String sortBy = "name";

        List<Employee> employees = Arrays.asList(
                new Employee("xxxx", "Alice", 202L,"delhi"),
                new Employee("xxxx", "Bob", 121L,"mumbai")
        );

        Page<Employee> employeePage = new PageImpl<>(employees);

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        when(empRepository.findAll(pageable)).thenReturn(employeePage);

        // Act
        Page<Employee> result = empService.getSortedEmplpyee(pageNo, sortBy, pageSize);

        // Assert
        assertEquals(2, result.getContent().size());
        assertEquals("Alice", result.getContent().get(0).getEmpName());
        verify(empRepository).findAll(pageable);
    }

    @Test
    void getEmployeeEmplCodeBetween() {
        Long minEmpCode = 0L;
        Long maxEmpCode = 2L;
        List<Employee> employees = Arrays.asList(
                new Employee("xxxx", "Alice", 202L,"delhi"),
                new Employee("xxxx", "Bob", 121L,"mumbai")
        );

        when(mongoTemplate.find(any(Query.class), eq(Employee.class))).thenReturn(employees);

        List<Employee> employeeEmplCodeBetween = empService.getEmployeeEmplCodeBetween(minEmpCode, maxEmpCode);

        assertEquals(2,employeeEmplCodeBetween.size());
        assertEquals("Alice",employeeEmplCodeBetween.get(0).getEmpName());
        verify(mongoTemplate).find(any(Query.class),eq(Employee.class));


    }
}