<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" uri="tag/user" %>
<%@ page pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="content" />
<head>
    <title><fmt:message key="common.personal"/></title>
    <c:import url="/WEB-INF/jsp/fragment/header.jsp" charEncoding="utf-8"/>
    <fmt:message key="admin.deleteSure" var="sure"/>
    <script>
        function deleteUser(name, surname) {
            return confirm('${sure}' + ' ' + name + ' ' + surname + "?");
        }
    </script>
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
    <div style="margin-top: 50px">
        <table class="table">
            <thead>
            <tr>
                <th><fmt:message key="common.name"/></th>
                <th><fmt:message key="common.surname"/></th>
                <th><fmt:message key="admin.role"/></th>
                <th><fmt:message key="admin.orders"/></th>
                <th><fmt:message key="admin.delete"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.users}" varStatus="i" var="user">
                <tr>
                    <td><c:out value="${user.name}"/></td>
                    <td><c:out value="${user.surname}"/></td>
                    <td>
                        <form name="roleForm${user.id}" action="controller" method="post"
                              style="text-align: center; margin: 0">
                            <input type="hidden" name="command" value="role"/>
                            <input type="hidden" name="userId" value="${user.id}"/>
                            <select id="roleForm" name="role" onchange="mySubmit('roleForm${user.id}')"
                                    style="font-size: 100%">
                                <option value="ADMIN" ${user.role == 'ADMIN' ? 'selected' : ''}>
                                    <fmt:message key="admin.admin"/>
                                </option>
                                <option value="TRAINER" ${user.role == 'TRAINER' ? 'selected' : ''}>
                                    <fmt:message key="admin.trainer"/>
                                </option>
                                <option value="VISITOR" ${user.role == 'VISITOR' ? 'selected' : ''}>
                                    <fmt:message key="admin.visitor"/>
                                </option>
                            </select>
                        </form>
                    </td>
                    <td>
                        <c:if test="${user.role == 'VISITOR'}">
                            <form action="order" method="get" class="my-auto">
                                <input type="hidden" name="userId" value="${user.id}">
                                <button type="submit" class="btn btn-success mx-auto">
                                    <fmt:message key="admin.look"/>
                                </button>
                            </form>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${user.role != 'ADMIN'}">
                            <form action="controller" method="post" class="my-auto"
                                  onsubmit="return deleteUser('${user.name}', '${user.surname}')">
                                <input type="hidden" name="command" value="delete">
                                <input type="hidden" name="userId" value="${user.id}">
                                <button type="submit" class="btn btn-secondary mx-auto">
                                    <fmt:message key="common.image" var="img"/>
                                    <img src="${pageContext.request.contextPath}/resources/img/delete.png" alt="${img}"
                                         style="width: 25px; height: 25px;">
                                </button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
