<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="content" />
<head>
    <title>JSP/JSTL i18n demo</title>
</head>
<body class="bg-light">
<div class="container">
    <div class="container mt-5" style="background-color: #dfdb9d">
        <div class="row justify-content-between">
            <div class=" my-auto col-9">
                <p style="font-size: 35px">${requestScope.user.name} ${requestScope.user.surname}</p>
            </div>
            <form action="controller" method="post" class="my-auto text-right mr-3">
                <fmt:message key="login.logOut" var="logOut"/>
                <input type="hidden" name="command" value="logOut">
                <input type="submit" value="${logOut}" class="btn btn-secondary">
            </form>
        </div>
    </div>
</body>
