<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page errorPage="erreurs/error.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Présence UCBL</title>
  <link rel="stylesheet" type="text/css" href="static/presence.css">
</head>
<body>
<%@ include file="composants/header.jsp" %>

<main class="wrapper">
  <jsp:include page="composants/menu.jsp"/>
  <article class="contenu">
    <c:choose>
      <c:when test="${param.contenu == null }">
        <jsp:include page="contenus/default.jsp"/>
      </c:when>
      <c:otherwise>
        <jsp:include page="contenus/${param.contenu}.jsp"/>
      </c:otherwise>
    </c:choose>
  </article>
</main>

<%@ include file="composants/footer.html" %>
</body>
</html>