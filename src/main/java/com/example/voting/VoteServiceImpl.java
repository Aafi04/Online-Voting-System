package com.example.voting;

public class VoteServiceImpl implements VoteService {
    private final VoteDao voteDao;

    public VoteServiceImpl(VoteDao voteDao) {
        this.voteDao = voteDao;
    }

    @Override
    public void saveVote(Vote vote) {
        voteDao.saveVote(vote);
    }
}
