package com.example.voting;

import java.sql.SQLException;

public class VoteServiceImpl implements VoteService {

    private final VoteDao voteDao;

    public VoteServiceImpl(VoteDao voteDao) {
        this.voteDao = voteDao;
    }

    @Override
    public void recordVote(String username, int candidateId) throws SQLException {
        voteDao.recordVote(username, candidateId);
    }
}
