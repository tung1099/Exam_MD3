<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 12/04/2022
  Time: 9:59 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Product Management Application</title>
</head>
<body>
<center>
    <h1>Product Management</h1>
    <h2>
        <a href="/product?action=createProduct">Add New Product</a>
    </h2>
</center>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of Products</h2></caption>
        <tr>
            <th>#</th>
            <th>Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Color</th>
            <th>Category</th>
            <th>Action</th>
        </tr>
        <c:forEach var="products" items="${products}">
            <tr>
                <td><c:out value="${products.id}"/></td>
                <td><c:out value="${products.name}"/></td>
                <td><c:out value="${products.price}"/></td>
                <td><c:out value="${products.quantity}"/></td>
                <td><c:out value="${products.color}"/></td>
                <td><c:out value="${products.category.id}"/></td>
                <td>
                    <a href="/product?action=editProduct&id=${products.id}">Edit</a>
                    <a href="/product?action=deleteProduct&id=${products.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
