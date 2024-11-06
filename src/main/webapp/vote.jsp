<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Vote</title>
</head>
<body>
    <h2>Cast Your Vote</h2>
    <form action="vote" method="post">
        <label for="userId">User ID:</label>
        <input type="text" id="userId" name="userId" required>
        <br><br>
        <label for="candidateId">Select Candidate:</label>
        <select name="candidateId" id="candidateId">
            <option value="1">Candidate 1</option>
            <option value="2">Candidate 2</option>
            <option value="3">Candidate 3</option>
            <!-- Add more candidates as needed -->
        </select>
        <br><br>
        <input type="submit" value="Vote">
    </form>
</body>
</html>