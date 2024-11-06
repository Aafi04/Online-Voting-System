package com.example.voting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VoteServlet extends HttpServlet {

    private VoteService voteService;

    @Override
    public void init() throws ServletException {
        // Create a VoteDao instance (replace with your implementation)
        VoteDao voteDao = new JdbcVoteDao(); // Assuming you have a JdbcVoteDao class

        // Create a VoteService instance with the VoteDao
        voteService = new VoteServiceImpl(voteDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/vote.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int candidateId = Integer.parseInt(request.getParameter("candidateId"));

        Vote vote = new Vote(userId, candidateId); // More concise object creation

        voteService.saveVote(vote);

        // Redirect to a confirmation page or display a success message
        response.sendRedirect("vote_success.jsp");
    }
}
