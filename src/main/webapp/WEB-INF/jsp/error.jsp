<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" isErrorPage="true" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<html>
<head>
    <title>Error</title>
    <c:import url="/WEB-INF/jsp/header.jsp" charEncoding="utf-8"/>
</head>
<body bgcolor="bg-light">
<div class="jumbotron">
    <h1 class="display-4"><fmt:message key="error.sorry"/></h1>
    <hr class="my-4">
    <p><fmt:message key="error.request"/> ${pageContext.errorData.requestURI} <fmt:message key="error.fail"/></p>
    <p><fmt:message key="error.servlet"/>: ${pageContext.errorData.servletName}</p>
    <p><fmt:message key="error.code"/>: ${pageContext.errorData.statusCode}</p>
    <form action="controller">
        <input type="hidden" name="command" value="main">
        <fmt:message key="error.main" var="main"/>
        <input type="submit" value="${main}" class="btn btn-primary">
    </form>
</div>
</body>
</html>
