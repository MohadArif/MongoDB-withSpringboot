package com.example.mongoDB.service;

import com.example.mongoDB.entity.TeamLearder;

import java.util.List;

public interface TeamLeaderService {
    void createTeamLeader(TeamLearder teamLearder);

    List<TeamLearder> getAllLeader();
}
