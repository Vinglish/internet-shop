<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
    <h1>Hello! Provide your user details!</h1>
    <div style="color: #d70707">${message}</div>
    <form method="post">
        Name: <input type="text" name="name">
        Password: <input type="text"name="pwd">
        Repeat password: <input type="text" name="repeatPwd">
        <button type="submit">Registration</button>
    </form>
</body>
</html>
