<%@ page import="classes.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<header class="wrapper">
    <div class="header-titre"><strong>Présence UCBL</strong></div>
    <c:if test="${sessionScope.user != null}">
        <div class="header-user"><a href="${sessionScope.user.admin ? 'admin?contenu=user&login='.concat(sessionScope.user.login) : 'presence?contenu=user&login='.concat(sessionScope.user.login)}"><%= ((User) (session.getAttribute("user"))).getLogin() %></a></div>
    </c:if>
</header>