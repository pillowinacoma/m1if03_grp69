<%@ page import="classes.User" %>
<%@ page import="classes.Salle" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.ParseException" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page errorPage="erreurs/error.jsp" %>
<%--
<c:if test="${!sessionScope.user.admin}">
  <% response.sendRedirect("interface.jsp"); %>
</c:if>
--%>
<jsp:useBean id="gestionPassages" type="classes.GestionPassages" scope="application"/>

<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Présence UCBL</title>
  <link rel="stylesheet" type="text/css" href="static/presence.css">
</head>
<body>
<jsp:include page="composants/header.jsp"/>

<main class="wrapper">
  <jsp:include page="composants/menu_admin.jsp"/>
  <article class="contenu">
    <c:choose>
      <c:when test="${param.contenu == null }">
        <jsp:include page="contenus/default_admin.jsp"/>
      </c:when>
      <c:when test="${param.contenu == \"salle_passage_form\"}">

        <jsp:include page="contenus/salle_passage_form.jsp"/>
      </c:when>
      <c:when test="${param.contenu == \"user\"}">
        <jsp:include page="contenus/user.jsp?login=${param.login}"/>
      </c:when>
      <c:otherwise>
        <jsp:include page="contenus/${param.contenu}.jsp"/>
      </c:otherwise>
    </c:choose>
  </article>
</main>

<jsp:include page="composants/footer.html"/>
</body>
</html>