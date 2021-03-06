<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" isErrorPage="true" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<html>
<head>
    <title><fmt:message key="error.title"/></title>
    <c:import url="/WEB-INF/jsp/fragment/header.jsp" charEncoding="utf-8"/>
</head>
<body bgcolor="bg-light">
<div class="jumbotron">
    <c:choose>
        <c:when test="${pageContext.errorData.statusCode == 400}">
            <h1 class="display-4"><fmt:message key="error.hack"/></h1>
        </c:when>
        <c:when test="${pageContext.errorData.statusCode == 401}">
            <h1 class="display-4"><fmt:message key="error.access"/></h1>
        </c:when>
        <c:when test="${pageContext.errorData.statusCode == 500}">
            <h1 class="display-4"><fmt:message key="error.serverError"/></h1>
        </c:when>
        <c:otherwise>
            <h1 class="display-4"><fmt:message key="error.sorry"/></h1>
        </c:otherwise>
    </c:choose>
    <hr class="my-4">
    <p><fmt:message key="error.request"/><c:out value="${pageContext.errorData.requestURI}"/> <fmt:message key="error.fail"/></p>
    <p><fmt:message key="error.servlet"/>: <c:out value="${pageContext.errorData.servletName}"/></p>
    <p><fmt:message key="error.code"/>: <c:out value="${pageContext.errorData.statusCode}"/></p>
    <a href="${pageContext.request.contextPath}" class="btn btn-primary">
        <fmt:message key="error.main"/>
    </a>
</div>
</body>
</html>
