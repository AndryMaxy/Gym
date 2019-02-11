<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error</title>
    <jsp:include page="header.jsp"/>
</head>
<body bgcolor="bg-light">
<div class="jumbotron">
    <h1 class="display-4">Sorry. This page does not exist.</h1>
    <hr class="my-4">
    <p class="lead">
        <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/index.jsp" role="button">Back to main page</a>
    </p>
</div>
</body>
</html>
