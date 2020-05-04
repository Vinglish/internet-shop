<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h1>Login page</h1>
    <h3>${errorMessage}</h3>
    <form method="post">
        Login: <input type="text" name="login">
        Password: <input type="text"name="pwd">
        <button type="submit">Login</button>
    </form>
</body>
</html>
