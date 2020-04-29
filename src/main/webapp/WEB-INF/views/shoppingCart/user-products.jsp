<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User products!</title>
</head>
<body>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Delete</th>
        </tr>
        <c:forEach var ="product" items = "${productsInCart}">
            <tr>
                <td>
                    <c:out value ="${product.id}"/>
                </td>
                <td>
                    <c:out value ="${product.name}"/>
                </td>
                <td>
                    <c:out value ="${product.price}"/>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}
                    /products/delete-product-from-shopping-cart
                    ?user-id=1&product-id=${product.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="${pageContext.request.contextPath}
                    /orders/create-order?user-id=1">Create order</a>
</body>
</html>
