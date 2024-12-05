package com.example.voting;

import java.sql.SQLException;

public interface VoteService {
    void recordVote(String username, int candidateId) throws SQLException;
}
