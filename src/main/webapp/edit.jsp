<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2/17/2024
  Time: 2:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit book</title>
</head>
<body>
<p>
    <a href="/books">Back to books list</a>
</p>
<form method="post">
    <h3>Edit book</h3>
    <fieldset>
        <legend>Book information</legend>
        <table>
            <tr>
                <td>Name:</td>
                <td><input type="text" name="name" value="${requestScope["books"].getName()}"></td>
            </tr>
            <tr>
                <td>Author:</td>
                <td><input type="text" name="author" value="${requestScope["books"].getAuthor()}"></td>
            </tr>
            <tr>
                <td>Description:</td>
                <td><input type="text" name="description" value="${requestScope["books"].getDescription()}"></td>
            </tr>
            <tr>
                <td>
                    <select name="categories" id="categories" multiple>
                        <c:forEach items="${categories}" var="c">
                            <option value="${c.id}">${c.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="Edit book">
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>
