<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset = "UTF-8">
    <title>Registration</title>
    <%@ include file = "/WEB-INF/jspf/style.jspf"%>
    <link href="https://fonts.googleapis.com/css2?family=Raleway:wght@200&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>
<body>
<header>
    <div class="logo">
        <a href="${pageContext.request.contextPath}/main"><i class="material-icons" id = "graphiclogo">store</i></a>
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
<a href="${pageContext.request.contextPath}/admin-panel"><fmt:message key="admin.panel"/></a>            </c:if>
            <a href="#" id="menu" class="icon">&#9776;</a>
            <a href="${pageContext.request.contextPath}?lang=en">
                EN
            </a>

            <a href="${pageContext.request.contextPath}?lang=ua">
                UA
            </a>
        </nav>
        <a href="${pageContext.request.contextPath}/cart"><span class="material-icons" id="cart">shopping_cart</span></a>
    </div>

</header>
<main>
    <div class="advantages-container">
        <h1 class ="form-header"><fmt:message key="registration.form"/></h1>
        <form action = "${pageContext.request.contextPath}/registration-post" method="post">

            <c:if test="${requestScope.validationErrors.contains('firstName')}">
                <p class="error_messages"><fmt:message key="registration.error.first.name"/></p>
            </c:if>
            <p><input type="text" name="first_name" class="feedback-input" placeholder="<fmt:message key="registration.first.name"/>" id = "f_name"></p>

            <c:if test="${requestScope.validationErrors.contains('lastName')}">
                <p class="error_messages"><fmt:message key="registration.error.last.name"/></p>
            </c:if>
            <p><input type="text" id="l_name" name="last_name" class="feedback-input" placeholder="<fmt:message key="registration.last.name"/>" id = "l_name"></p>


            <c:if test="${requestScope.validationErrors.contains('email')}">
                <p class="error_messages"><fmt:message key="registration.error.email"/></p>
            </c:if>
            <p><input type="text" id="email" name="email" class="feedback-input" placeholder="<fmt:message key="registration.email"/>" id = "email"></p>


            <c:if test="${requestScope.validationErrors.contains('password')}">
                <p class="error_messages"><fmt:message key="registration.error.password"/></p>
            </c:if>
            <p><input type="text" id="password" name="password" class="feedback-input" placeholder="<fmt:message key="registration.password"/>" id = "password"></p>

            <button type="submit" class="button-submit" value="Register!"><p class="button-text"><fmt:message key="registration.register"/></p></button>

        </form>
    </div>
</main>
</body>
</html>