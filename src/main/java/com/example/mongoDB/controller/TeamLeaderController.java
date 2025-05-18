package com.example.mongoDB.controller;

import com.example.mongoDB.entity.TeamLearder;
import com.example.mongoDB.service.TeamLeaderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/teamleader")
@Tag(name = "TeamLeader_Controller")
public class TeamLeaderController {

    private final TeamLeaderService teamLeaderService;

    public TeamLeaderController(TeamLeaderService teamLeaderService) {
        this.teamLeaderService = teamLeaderService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTeamLeader(@RequestBody TeamLearder teamLearder){
        log.info("this teamleader class {}",teamLearder.getTlName());
        teamLeaderService.createTeamLeader(teamLearder);
        return new ResponseEntity<>("created!!", HttpStatus.OK);
    }

    @GetMapping("/getAllTeamLeader")
    public ResponseEntity<?> getAllTeamLeader(){
        List<TeamLearder> allLeader = teamLeaderService.getAllLeader();
        return new ResponseEntity<>(allLeader,HttpStatus.OK);
    }
}
