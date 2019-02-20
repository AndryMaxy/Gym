<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${sessionScope.role != 'GUEST'}">
        <c:redirect url="home"/>
    </c:when>
    <c:otherwise>
        <jsp:forward page="WEB-INF/jsp/common/login.jsp"/>
    </c:otherwise>
</c:choose>