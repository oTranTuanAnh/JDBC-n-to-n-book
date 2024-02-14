<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2/13/2024
  Time: 3:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Them sach</title>
</head>
<body>
<form method="post">
    <input type="text" name="name" placeholder="name">
    <input type="text" name="author" placeholder="author">
    <input type="text" name="description" placeholder="description">

    <select name="categories" id="categories" multiple>
        <c:forEach items="${categories}" var="c">
            <option value="${c.id}">${c.name}</option>
        </c:forEach>
    </select>
    <input value="tao moi" type="submit">
</form>
</body>
</html>
