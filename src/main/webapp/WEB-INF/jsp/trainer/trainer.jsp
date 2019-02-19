<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<head>
    <title><fmt:message key="common.personal"/></title>
    <c:import url="/WEB-INF/jsp/fragment/header.jsp" charEncoding="utf-8"/>
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
    <div class="row" style="margin-top: 100px">
        <p>List new visitors</p>
    </div>
    <div class="row">
        <div class="col-8 mx-auto">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th><fmt:message key="register.name"/></th>
                    <th><fmt:message key="register.surname"/></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.users}" var="user">
                    <tr>
                        <td><c:out value="${user.name}"/></td>
                        <td><c:out value="${user.surname}"/></td>
                        <td>
                            <form action="doAppoint" method="get" class="my-auto">
                                <%--<input type="hidden" name="command" value="doAppoint">--%>
                                <input type="hidden" name="userId" value="${user.id}">
                                <button type="submit" class="btn btn-success mx-auto">
                                    <fmt:message key="trainer.appoint"/>
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
