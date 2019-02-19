<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="${sessionScope.locale}">
<c:choose>
    <c:when test="${sessionScope.role == 'ADMIN'}">
        <c:import url="/WEB-INF/jsp/admin/admin.jsp" charEncoding="utf-8"/>
    </c:when>
    <c:when test="${sessionScope.role == 'TRAINER'}">
        <c:import url="/WEB-INF/jsp/trainer/trainer.jsp" charEncoding="utf-8"/>
    </c:when>
    <c:when test="${sessionScope.role == 'VISITOR'}">
        <c:import url="/WEB-INF/jsp/visitor/visitor.jsp" charEncoding="utf-8"/>
    </c:when>
    <c:when test="${sessionScope.role == 'GUEST'}">
        <c:import url="/WEB-INF/jsp/common/login.jsp" charEncoding="utf-8"/>
    </c:when>
    <c:otherwise>
        <c:import url="/WEB-INF/jsp/common/error.jsp" charEncoding="utf-8"/>
    </c:otherwise>
</c:choose>
</html>
