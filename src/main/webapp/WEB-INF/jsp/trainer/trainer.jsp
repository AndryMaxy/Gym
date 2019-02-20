<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<head>
    <title><fmt:message key="common.personal"/></title>
    <c:import url="/WEB-INF/jsp/fragment/header.jsp" charEncoding="utf-8"/>
</head>
<body>
<form name="lang" action="controller" style="text-align: right; margin: 0">
    <input type="hidden" name="command" value="locale"/>
    <select id="language" name="locale" onchange="mySubmit()" style="font-size: 130%">
        <option value="ru-RU" ${sessionScope.locale.toLanguageTag() == 'ru-RU' ? 'selected' : ''}>Русский</option>
        <option value="en-US" ${sessionScope.locale.toLanguageTag() == 'en-US' ? 'selected' : ''}>English</option>
        <option value="be-BY" ${sessionScope.locale.toLanguageTag() == 'be-BY' ? 'selected' : ''}>Беларускі</option>
    </select>
    <script>
        function mySubmit() {
            document.lang.submit();
        }
    </script>
</form>
<div class="container mainCont">
    <div class="hat">
        <div class="row justify-content-between align-items-center">
            <div class="col-md-auto">
                <p class="name">${requestScope.user.name} ${requestScope.user.surname}</p>
            </div>
            <form action="controller" method="post" class="col-md-auto"
                  style="margin: 0; margin-right: 25px; padding: 0;">
                <fmt:message key="common.logOut" var="logOut"/>
                <input type="hidden" name="command" value="logOut">
                <input type="submit" value="${logOut}" class="btn btn-outline-primary">
            </form>
        </div>
    </div>
    <c:choose>
        <c:when test="${requestScope.users.size() == 0}">
            <div class="row" style="margin-top: 75px">
                <div class="mx-auto">
                    <h3><fmt:message key="trainer.noVisitors"/></h3>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="row" style="margin-top: 75px">
                <div class="mx-auto">
                    <h3><fmt:message key="trainer.awaitingVisitors"/></h3>
                </div>
            </div>
            <div class="row" style="margin-top: 5px">
                <div class="col-8 mx-auto">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th><fmt:message key="common.name"/></th>
                            <th><fmt:message key="common.surname"/></th>
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
        </c:otherwise>
    </c:choose>
</div>
</body>
