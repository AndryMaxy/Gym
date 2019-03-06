<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="tag/user" %>

<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<head>
    <title><fmt:message key="common.personal"/></title>
    <c:import url="/WEB-INF/jsp/fragment/header.jsp" charEncoding="utf-8"/>
</head>
<body>
<c:import url="/WEB-INF/jsp/fragment/locale.jsp" charEncoding="utf-8"/>
<div class="container mainCont">
    <c:import url="/WEB-INF/jsp/fragment/hat.jsp" charEncoding="utf-8"/>
    <div class="botHat">
        <div class="col-md-auto">
            <p class="name"><my:userTag name="${requestScope.user.name}" surname="${requestScope.user.surname}"/></p>
        </div>
    </div>
    <c:choose>
        <c:when test="${requestScope.users.size() == 0}">
            <div class="row" style="margin-top: 75px">
                <div class="mx-auto">
                    <h3><fmt:message key="trainer.noVisitors"/></h3>
                </div>
            </div>
            <div class="row" style="margin-top: 40px">
                <div class="mx-auto">
                    <fmt:message key="common.image" var="img"/>
                    <img src="${pageContext.request.contextPath}/resources/img/empty.jpg" alt="${img}">
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
                    <table class="table">
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
