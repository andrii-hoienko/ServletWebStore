<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>WebStore</title>
    <%@ include file="/WEB-INF/jspf/style.jspf" %>
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@200&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>
<body>
<header>
    <div class="logo">
        <a href="${pageContext.request.contextPath}/main"><i class="material-icons" id="graphiclogo">store</i></a>
        <nav class="topnav" id="mytopnav">
            <a href="${pageContext.request.contextPath}/profile"><fmt:message key="profile"/></a>

            <a href="#"><fmt:message key="news"/></a>
            <a href="#"><fmt:message key="about.us"/></a>
            <c:if test="${sessionScope.userRole == null}">
                <a href="${pageContext.request.contextPath}/login"><fmt:message key="login"/></a>
                <a href="${pageContext.request.contextPath}/registration"><fmt:message key="registration"/></a>
            </c:if>
            <c:if test="${sessionScope.userRole != null}">
                <a href="${pageContext.request.contextPath}/logout"><fmt:message key="logout"/></a>
            </c:if>
            <c:if test="${sessionScope.userRole == 'ADMIN'}">
<a href="${pageContext.request.contextPath}/admin-panel"><fmt:message key="admin.panel"/></a>            </c:if>
            <a href="#" id="menu" class="icon">&#9776;</a>
            <a href="${pageContext.request.contextPath}?lang=en">
                EN
            </a>

            <a href="${pageContext.request.contextPath}?lang=ua">
                UA
            </a>
        </nav>
        <a href="${pageContext.request.contextPath}/cart"><span class="material-icons"
                                                                id="cart">shopping_cart</span></a>
    </div>

</header>
<main>
    <div class="advantages-container">
        <c:if test="${requestScope.orders.size() != 0}">
            <table>
                <tr>
                    <th><fmt:message key="order.number"/></th>
                    <th><fmt:message key="order.date"/></th>
                    <th><fmt:message key="product.name"/></th>
                    <th><fmt:message key="products.price"/></th>
                    <th><fmt:message key="amount"/></th>
                    <th><fmt:message key="status"/></th>
                </tr>
                <c:forEach items="${requestScope.orders}" var="order">
                    <c:forEach items="${order.getProductMap().entrySet()}" var="product">
                        <tr>
                            <td>${order.getId()}</td>
                            <td>${order.getDate()}</td>
                            <td>${product.getKey().getId()}</td>
                            <td>${product.getKey().getPrice()}</td>
                            <td>${product.getValue()}</td>
                            <td>${order.getStatus()}</td>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${requestScope.orders.size() == 0}">
            <h1 class="form-header"><fmt:message key="profile.empty"/></h1>
            <form action="${pageContext.request.contextPath}/main" method="get">
                <button type="submit" class="button-submit"><fmt:message key="to.store"/></button>
            </form>
        </c:if>
    </div>
</main>
</body>
</html>
