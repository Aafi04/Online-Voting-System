package com.example.voting;

public class Vote {
    private int id; // Unique identifier for the vote (optional)
    private int userId; // User who cast the vote
    private int candidateId; // Candidate the user voted for
    private String electionId; // Election for which the vote was cast (optional)

    // Getters and setters for the fields
    public Vote(int userId, int candidateId) {
        this.userId = userId;
        this.candidateId = candidateId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }

    public String getElectionId() {
        return electionId;
    }

    public void setElectionId(String electionId) {
        this.electionId = electionId;
    }
}
