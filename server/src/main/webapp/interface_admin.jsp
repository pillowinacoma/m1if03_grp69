<%--
  Created by IntelliJ IDEA.
  User: mahmoud
  Date: 31/10/2020
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="classes.Passage" %>
<%@ page import="classes.GestionPassages" %>
<%@ page import="classes.Salle" %>
<%@ page import="java.util.Date" %>
<%@ page import="classes.User" %>
<%@ page import="java.util.List" %>
<html>
<head>
  <title>Menu</title>
</head>
<body>

<h1> MENU </h1>

<c:url value="passage.jsp" var="passageUrlUser"/>
<c:url value="passage_admin.jsp" var="passageUrlAdm"/>
<c:url value="user.jsp" var="userUrl"/>
<c:url value="Deco" var="logoutUrl"/>



<div>

</div>

<div class="menu">
  <ul>
<c:choose>


  <c:when test="${!sessionScope.admin}">
    <li><a href="${passageUrlUser}">Passage</a></li>

  </c:when>
  <c:otherwise>
    <li><a href="${passageUrlAdm}">Passage</a></li>
    <br />
  </c:otherwise>
</c:choose>


    <li><a href="${userUrl}">User</a></li>


    <li><a href="${logoutUrl}">Déconnexion</a></li>
  </ul>
  <br style="clear:left"/>
</div>


</body>
</html>

