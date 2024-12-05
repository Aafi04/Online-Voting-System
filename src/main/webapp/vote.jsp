<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Vote</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container">
        <h2 class="mt-5">Vote</h2>
        <form action="vote" method="post">
            <div class="form-group">
                <label for="candidate_id">Candidate ID:</label>
                <input type="text" class="form-control" id="candidate_id" name="candidate_id" required>
            </div>
            <button type="submit" class="btn btn-primary">Submit Vote</button>
        </form>
    </div>
</body>
</html>