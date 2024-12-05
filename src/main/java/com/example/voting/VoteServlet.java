package com.example.voting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class VoteServlet extends HttpServlet {

    private VoteService voteService;

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
        HttpSession session = request.getSession(false); // Use false to avoid creating a new
                                                         // session if one doesn't exist
        if (session == null || session.getAttribute("username") == null) {
            System.out
                    .println("Session is null or username is not set. Redirecting to login page.");
            response.sendRedirect("login.jsp");
            return;
        }

        String username = (String) session.getAttribute("username");
        String candidateId = request.getParameter("candidate_id");

        System.out.println("Username from session: " + username);
        System.out.println("Candidate ID from request: " + candidateId);

        try {
            voteService.recordVote(username, Integer.parseInt(candidateId));
            response.setStatus(HttpServletResponse.SC_OK);
            System.out.println("Vote recorded successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
