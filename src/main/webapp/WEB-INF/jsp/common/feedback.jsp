<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<html>
<head>
    <c:import url="/WEB-INF/jsp/fragment/header.jsp" charEncoding="utf-8"/>
    <title><fmt:message key="hat.feedback"/></title>
</head>
<body>
<c:import url="/WEB-INF/jsp/fragment/locale.jsp" charEncoding="utf-8"/>
<div class="container mainCont">
    <c:import url="/WEB-INF/jsp/fragment/hat.jsp" charEncoding="utf-8"/>
    <c:if test="${sessionScope.role == 'VISITOR' && requestScope.booking != null && requestScope.booking.feedback == null}">
        <form action="controller" method="post" style="margin-top: 25px" accept-charset="UTF-8">
            <input type="hidden" name="command" value="addFeedback">
            <div class="row">
                <div class="col-8 mx-auto">
                    <div class="col">
                        <div class="form-group">
                            <label for="feed" style="margin-bottom: 0"><fmt:message key="feedback.field"/></label>
                            <textarea name="feedbackArea" minlength="20" maxlength="500" class="form-control" id="feed" rows="3"
                                      style="resize: none"></textarea>
                        </div>
                    </div>
                    <c:if test="${param.error == 'true'}">
                        <div class="col" style="margin-bottom: 15px; text-align: center">
                            <div class="mx-auto" style="text-align: center">
                                <fmt:message key="feedback.incorrect"/>
                            </div>
                        </div>
                    </c:if>
                    <div class="col" style="text-align: right">
                        <fmt:message key="feedback.submit" var="submit"/>
                        <input type="submit" value="${submit}" class="btn btn-success">
                    </div>
                </div>
            </div>
        </form>
    </c:if>
    <div class="col-10 mx-auto" style="margin-top: 25px">
        <h3 style="text-align: center"><fmt:message key="feedback.list"/></h3>
        <table class="table" style="border: 0; margin-top: 15px">
            <tbody>
            <c:forEach items="${requestScope.bookings}" var="booking">
                <c:if test="${booking.feedback != null}">
                <tr>
                    <td style="width: 15%"><c:out value="${booking.user.name}"/></td>
                    <td style="text-align: left"><c:out value="${booking.feedback}"/></td>
                </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
