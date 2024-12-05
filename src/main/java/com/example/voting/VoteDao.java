package com.example.voting;

import java.sql.SQLException;

public interface VoteDao {
    void recordVote(String username, int candidateId) throws SQLException;
}
