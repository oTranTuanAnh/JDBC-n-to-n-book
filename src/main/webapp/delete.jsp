<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2/17/2024
  Time: 9:39 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete</title>
</head>
<body>
<p>
    <a href="/books">Back to books list</a>
</p>
<form method="post">
<h3>Are you sure???</h3>
    <fieldset>
        <legend>Book information</legend>
        <table>
            <tr>
                <td>Name:</td>
                <td>${requestScope["books"].getName()}</td>
            </tr>
            <tr>
                <td>Author:</td>
                <td>${requestScope["books"].getAuthor()}</td>
            </tr>
            <tr>
                <td>Description:</td>
                <td>${requestScope["books"].getDescription()}</td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="Delete book">
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>
