<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<div class="top-nav">
    <div class="row justify-content-between align-items-center">
        <div class="col-md-auto">
            <div>
                <fmt:message key="common.image" var="img"/>
                <a href="${pageContext.request.contextPath}">
                    <img class="logo" alt="${img}" src="${pageContext.request.contextPath}/resources/img/logo.png">
                </a>
            </div>
        </div>
        <div class="col-md-auto">
            <ul class="nav justify-content-end align-items-center">
                <c:if test="${sessionScope.role == 'VISITOR'}">
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/refill">
                            <fmt:message key="hat.refill"/>
                        </a>
                    </li>
                </c:if>
                <li class="nav-item">
                    <a class="nav-link active" href="${pageContext.request.contextPath}/feedback">
                        <fmt:message key="hat.feedback"/>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/about">
                        <fmt:message key="hat.about"/>
                    </a>
                </li>
                <c:if test="${sessionScope.role != 'GUEST'}">
                    <li class="nav-item">
                        <form action="controller" method="post" class="nav-link" style="margin-block-end: 0">
                            <fmt:message key="common.logOut" var="logOut"/>
                            <input type="hidden" name="command" value="logOut">
                            <input type="submit" value="${logOut}" class="btn btn-outline-primary">
                        </form>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</div>
