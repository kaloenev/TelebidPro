<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Password Reset</title>
</head>
<body>
<h2>Enter New Password</h2>
<form action=""<%= request.getContextPath() %>/setNewPass"" method="post">
  <input type="hidden" name="verificationLink" value="${param.verificationLink}">
  New Password: <input type="password" name="newPassword" required><br>
  <input type="submit" value="Reset Password">
</form>
</body>
</html>