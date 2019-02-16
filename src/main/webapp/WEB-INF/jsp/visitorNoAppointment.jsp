<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="content"/>
<h3 align="center"><fmt:message key="visitor.purchase"/></h3>
<table class="table table-bordered" style="text-align: center; vertical-align: center">
    <thead>
    <tr style="font-size: 110%">
        <th scope="col" style="width: 25%; vertical-align: middle"><fmt:message key="visitor.membership"/></th>
        <th scope="col" style="width: 25%; vertical-align: middle"><fmt:message key="visitor.visitCount"/></th>
        <th scope="col" style="width: 25%; vertical-align: middle"><fmt:message key="visitor.price"/></th>
        <th scope="col" style="width: 25%;"></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${applicationScope.memberships}" var="membership">
        <tr style="vertical-align: center">
            <td style="font-size: 140%;vertical-align: middle"><c:out value="${membership}"/></td>
            <td style="font-size: 140%;vertical-align: middle"><c:out value="${membership.count}"/></td>
            <td style="font-size: 140%;vertical-align: middle"><c:out value="${membership.price}"/></td>
            <td>
                <div class="container">
                    <form action="controller" method="post" class="row my-auto">
                        <input type="hidden" name="command" value="buy">
                        <input type="hidden" name="membership" value="${membership}">
                        <button type="submit" class="btn btn-success mx-auto">
                            <fmt:message key="visitor.buy"/>
                        </button>
                    </form>
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
