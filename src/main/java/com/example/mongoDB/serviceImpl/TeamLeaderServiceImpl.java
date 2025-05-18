package com.example.mongoDB.serviceImpl;

import com.example.mongoDB.entity.TeamLearder;
import com.example.mongoDB.repositroy.TeamLeaderRepo;
import com.example.mongoDB.service.TeamLeaderService;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
public class TeamLeaderServiceImpl implements TeamLeaderService {

    private final TeamLeaderRepo teamLeaderRepo;

    public TeamLeaderServiceImpl(TeamLeaderRepo teamLeaderRepo) {
        this.teamLeaderRepo = teamLeaderRepo;
    }

    @Override
    public void createTeamLeader(TeamLearder teamLearder) {
        DateFormatter dateFormatter=new DateFormatter();
        dateFormatter.setPattern("yyyy-MM-dd HH:mm:ss");
        TeamLearder teamLearder1 = new TeamLearder();
        teamLearder1.setTlName(teamLearder.getTlName());
        teamLearder1.setProjectName(teamLearder.getProjectName());
        teamLearder1.setTeamMember(teamLearder.getTeamMember());
        teamLearder1.setEmployeeList(teamLearder.getEmployeeList());
        teamLearder1.setCreatedDate(new Date());
//        teamLearder1.setCreatedDate(dateFormatter.setSource(Date.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant())));
        teamLeaderRepo.save(teamLearder1);
    }

    @Override
    public List<TeamLearder> getAllLeader() {
        return teamLeaderRepo.findAll();
    }
}
