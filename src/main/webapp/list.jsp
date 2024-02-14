<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2/13/2024
  Time: 3:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>DANH SACH</title>
</head>
<body>
<h1>Danh sach</h1>
<a href="/books?action=create" >Them moi</a>
<table border="1px solid">
    <tr>
        <th>STT</th>
        <th>TEN</th>
        <th>TAC GIA</th>
        <th>DANH MUC</th>
    </tr>
    <c:forEach items="${books}" var="books">
        <tr>
            <td>${books.id}</td>
            <td>${books.name}</td>
            <td>${books.author}</td>
            <td>
                <c:forEach items="${books.categoryList}" var="categories">
                    <span>${categories.name}</span> &nbsp;
                </c:forEach>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
