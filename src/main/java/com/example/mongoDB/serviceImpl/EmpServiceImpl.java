package com.example.mongoDB.serviceImpl;

import com.example.mongoDB.entity.Employee;
import com.example.mongoDB.exception.EmployeeNotFoundException;
import com.example.mongoDB.repositroy.EmpRepository;
import com.example.mongoDB.service.EmpService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class EmpServiceImpl implements EmpService {

    private final EmpRepository empRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public EmpServiceImpl(EmpRepository empRepository) {
        this.empRepository = empRepository;
    }

    @Override
    public void createEmployee(Employee employee) {
        empRepository.save(employee);
    }

    @Override
    public List<Employee> findAllEmp() {
        return empRepository.findAll();
    }

    @Override
    public Employee findEmployeeById(String id) {
        return empRepository.findById(id).orElseThrow(() -> {
            log.info("employee not find with this {}", id);
            return new EmployeeNotFoundException("employee is not found!!");
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateEmplyee(Employee employee) {
        Employee existingEmp = empRepository.findById(employee.getId())
                .orElseThrow(() -> new EmployeeNotFoundException("sorry employee not found"));

        log.info(existingEmp.getEmpName(), "{} fetched from database perfectly!!");
        if (existingEmp.getEmpName() != null && !existingEmp.getEmpName().equals(employee.getEmpName())) {
            existingEmp.setEmpName(employee.getEmpName());
            log.info(employee.getEmpName() + " updated into existing employee");
        }
        if (existingEmp.getEmpCode() != null && !existingEmp.getEmpCode().equals(employee.getEmpCode())) {
            existingEmp.setEmpCode(employee.getEmpCode());
            log.info(employee.getEmpCode() + " updated into existing employee");
        }
        if (existingEmp.getAddress() != null && !existingEmp.getAddress().equals(employee.getAddress())) {
            existingEmp.setAddress(employee.getAddress());
            log.info(employee.getAddress() + " updated into existing employee");
        }
        empRepository.save(existingEmp);
        log.info("data updated successfully...");
    }

    @Override
    public void deleteEmployee(String id) {
//        if(empRepository.findById(id).isPresent()){
//        empRepository.deleteById(id);
//        }else {
//            throw  new EmployeeNotFoundException("sorry id doesn't exist!!");
//        }

        Employee employee = empRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("sorry id doesn't exist!!"));
        empRepository.deleteById(employee.getId());
    }

    @Override
    public Employee findEmpByName(String name) {
        return empRepository.getEmployeeByName(name);
    }

    @Override
    public Employee findEmployeeByEmpCode(Long empCode) {
        log.info("data fetch successfully...!!");
        return empRepository.getEmployeeByEmpCode(empCode)
                .orElseThrow(() -> new EmployeeNotFoundException("sorry this empCode is not found!!"));
    }

    public List<Employee> findAllEmployeeBasedOnCity(String address) {
        Query query = new Query();
        query.addCriteria(Criteria.where("address").is(address));
        List<Employee> employees = mongoTemplate.find(query, Employee.class);
        if (employees.isEmpty()) {
            log.error("No employee found with address: {}", address);
            throw new EmployeeNotFoundException("No employee found with address:" + address);
        }
        return employees;
    }

    @Override
    public Page<Employee> getSortedEmplpyee(int pageNo, String sort, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sort));
        Page<Employee> employeePage = empRepository.findAll(pageable);
        log.info("data sorted successfully and page size {}", employeePage.getSize());
        return employeePage;
    }

    @Override
    public List<Employee> getEmployeeEmplCodeBetween(Long minEmpCode, Long maxEmpCode) {
        Query query = new Query();
        query.addCriteria(Criteria.where("empCode").gt(minEmpCode).lt(maxEmpCode));

//        we can add multiple query here.... example
//        query.addCriteria(Criteria.where("address").is("something"));  //here it also filter on the basis of these criteria

//        if you want to use Or operator then
//        Criteria criteria = new Criteria();
//        query.addCriteria(criteria.orOperator(Criteria.where("empCode").gt(minEmpCode).lt(maxEmpCode),
//                Criteria.where("address").is("something")
//        ));

        List<Employee> employees = mongoTemplate.find(query, Employee.class);
        if (employees.isEmpty()) {
            log.error("sorry there is employee between {} - {}", minEmpCode, maxEmpCode);
            throw new EmployeeNotFoundException("not found!!");
        }
        return employees;
    }
}
