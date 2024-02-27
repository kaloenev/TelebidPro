<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="ISO-8859-1">
  <title>User Login Form</title>
</head>
<body>
<div align="center">
  <h1>User Login Form</h1>
  <form action="<%=request.getContextPath()%>/login" method="post">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required><br><br>
    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br><br>
    <label for="captcha">Captcha:</label>
    <input type="text" id="captcha" name="captcha" required>
    <img src="captcha.jsp" alt="CAPTCHA"><br><br>
    <input type="submit" value="Login">
  </form>
</div>
</body>
</html>
