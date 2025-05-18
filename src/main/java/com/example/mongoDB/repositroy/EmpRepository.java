package com.example.mongoDB.repositroy;

import com.example.mongoDB.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpRepository extends MongoRepository<Employee,String> {

//    public Employee findByEmpName(String name);

//
    @Query(value = "{'empName':?0}")
    public Employee getEmployeeByName(@Param("name") String name);

    @Query(value = "{'empCode':?0}")
    public Optional<Employee> getEmployeeByEmpCode(Long empCode);
}
