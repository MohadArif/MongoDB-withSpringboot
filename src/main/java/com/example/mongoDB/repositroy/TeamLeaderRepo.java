package com.example.mongoDB.repositroy;

import com.example.mongoDB.entity.TeamLearder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamLeaderRepo extends MongoRepository<TeamLearder,String> {
}
