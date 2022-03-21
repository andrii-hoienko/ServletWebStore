<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
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
                <a href="${pageContext.request.contextPath}/admin-panel"><fmt:message key="admin.panel"/></a> </c:if>
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
        <table>
            <tr>
                <th><fmt:message key="name"/></th>
                <th><fmt:message key="color"/></th>
                <th><fmt:message key="amount"/></th>
                <th><fmt:message key="price"/></th>
                <th><fmt:message key="buy"/></th>
                <th>
                    <div class="dropdown">
                        <button class="dropbtn">
                            <fmt:message key="menu.title"/>
                        </button>
                        <div class="dropdown-content">
                            <a href="${pageContext.request.contextPath}?filter=a-z">
                                <fmt:message key="menu.name.sort"/>
                            </a>
                            <a href="${pageContext.request.contextPath}?filter=z-a">
                                <fmt:message key="menu.name.sort.reversed"/>
                            </a>
                            <a href="${pageContext.request.contextPath}?filter=price">
                                <fmt:message key="menu.price.sort"/>
                            </a>
                            <a href="${pageContext.request.contextPath}?filter=date">
                                <fmt:message key="menu.date.sort"/>
                            </a>
                        </div>
                    </div>
                </th>
            </tr>
            <c:forEach items="${requestScope.products}" var="product">
                <form action="${pageContext.request.contextPath}/mainAddToCart" method="put">
                    <tr>
                        <td>${product.getName()}</td>
                        <td>${product.getColor()}</td>
                        <td>

                            <input type="number" name="amount" min="1" max="100" value='1'/>

                        </td>
                        <td>${product.getPrice()}</td>
                        <td>
                            <input type="text" readonly="readonly" name="productId" style="visibility: hidden; width: 0"
                                   hidden value="${product.getId()}">
                            <button type="submit"><fmt:message key="add.to.cart"/></button>
                        </td>
                    </tr>
                </form>
            </c:forEach>
        </table>

        <div class="pagination">
            <c:forEach items="${requestScope.pagesArray}" var="page">
                <a href="${pageContext.request.contextPath}?page=${page}">${page}</a>
            </c:forEach>
        </div>
    </div>
</main>
</body>
</html>