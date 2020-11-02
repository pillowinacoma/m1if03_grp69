<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage = "true" %>

<c:choose>
    <c:when test="${pageContext.exception.getClass().getSimpleName() == 'ServletException' && pageContext.exception.message.contains('not found')}">
        <% response.sendError(HttpServletResponse.SC_NOT_FOUND); %>
    </c:when>
    <c:otherwise>
        <table>
            <tr>
                <td><b>Error:</b></td>
                <td>${pageContext.exception}</td>
            </tr>

            <tr>
                <td><b>URI:</b></td>
                <td>${pageContext.errorData.requestURI}</td>
            </tr>

            <tr>
                <td><b>Status code:</b></td>
                <td>${pageContext.errorData.statusCode}</td>
            </tr>

            <tr>
                <td><b>Stack trace:</b></td>
                <td>
                    <c:forEach var = "trace"
                               items = "${pageContext.exception.stackTrace}">
                        <p>${trace}</p>
                    </c:forEach>
                </td>
            </tr>
        </table>
    </c:otherwise>
</c:choose>