package com.example.mongoDB.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "teamleader")
@Getter
@Setter
public class TeamLearder {

    @Id
    private String id;
    private String tlName;
    private String projectName;
    private Long teamMember;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
    private Date createdDate;

    private List<Employee> employeeList;

}
