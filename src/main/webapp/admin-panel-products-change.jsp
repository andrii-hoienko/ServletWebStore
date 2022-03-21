<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>Registration</title>
    <%@ include file="/WEB-INF/jspf/style.jspf" %>
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@200&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>
<body>
<header>
    <div class="logo">
        <a href="${pageContext.request.contextPath}/main"><i class="material-icons" id="graphiclogo">store</i></a>
        <nav class="topnav" id="mytopnav">
            <a href="#">Cabinet</a>

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
        <h1 class="form-header"><fmt:message key="products.change.product.form"/></h1>
        <form action="${pageContext.request.contextPath}/admin-panel/products-change-post" method="post">


            <c:if test="${requestScope.validationErrors.contains('name')}">
                <p class="error_messages"><fmt:message key="products.error.name"/></p>
            </c:if>
            <p><input type="text" name="name" class="feedback-input" placeholder="Name"></p>

            <c:if test="${requestScope.validationErrors.contains('price')}">
                <p class="error_messages"><fmt:message key="products.error.price"/></p>
            </c:if>
            <p><input type="text" name="price" class="feedback-input" placeholder="Price"></p>

            <c:if test="${requestScope.validationErrors.contains('totalAmount')}">
                <p class="error_messages"><fmt:message key="products.error.amount"/></p>
            </c:if>
            <p><input type="text" name="totalAmount" class="feedback-input" placeholder="Total amount"></p>

            <c:if test="${requestScope.validationErrors.contains('color')}">
                <p class="error_messages"><fmt:message key="products.error.color"/></p>
            </c:if>
            <p><input type="text" name="color" class="feedback-input" placeholder="Color"></p>

            <input type="text" readonly="readonly" name="productId" style="visibility: hidden; width: 0"
                   hidden value="${requestScope.productId}">

            <button type="submit" class="button-submit"><fmt:message key="products.change"/></button>

        </form>
    </div>
</main>
</body>
</html>