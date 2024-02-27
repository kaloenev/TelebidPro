<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Edit Profile</title>
</head>
<body>
<h2>Edit Profile</h2>
<form action="editProfile" method="post">
  <label for="firstName">First Name:</label>
  <input type="text" id="firstName" name="firstName" value="${user.firstName}"><br>
  <label for="lastName">Last Name:</label>
  <input type="text" id="lastName" name="lastName" value="${user.lastName}"><br>
  <input type="submit" value="Save">
</form>
<br>
<a href="<%=request.getContextPath()%>/setPassResetLink">Reset Password</a>
<br>
<a href="<%=request.getContextPath()%>/deleteProfile">Delete Profile</a>
</body>
</html>