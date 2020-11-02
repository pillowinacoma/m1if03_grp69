<%@ page import="classes.User" %>
<%@ page import="classes.Salle" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page errorPage="erreurs/error.jsp" %>

<jsp:useBean id="passages" class="classes.GestionPassages" scope="application"/>
<jsp:useBean id="salles" class="java.util.HashMap" scope="application"/>
<jsp:useBean id="users" class="java.util.HashMap" type="java.util.Map<java.lang.String,classes.User>" scope="application"/>

<% // Très moche :
  // - on essaye d'ajouter l'utilisateur à chaque requête
  // - pas de contrôle sur les types des clés et des valeurs des entrées de la map
  User user = (User) session.getAttribute("user");
  if (!users.containsValue(user))
    users.put(user.getLogin(), user);
%>

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
  <jsp:include page="composants/menu.jsp"/>
  <article class="contenu">
    <c:choose>
      <c:when test="${param.contenu == null }">
        <jsp:include page="contenus/default.jsp"/>
      </c:when>
      <c:when test="${param.contenu == \"passages\"}">
        <%
          if (request.getParameter("nomSalle") != null)
            request.setAttribute("passagesAffiches", passages.getPassagesByUserAndSalle((User) session.getAttribute("user"), new Salle(request.getParameter("nomSalle"))));
          else
            request.setAttribute("passagesAffiches", passages.getPassagesByUser((User) session.getAttribute("user"))); %>
        <jsp:include page="contenus/passages.jsp"/>
      </c:when>
      <c:when test="${param.contenu == \"user\"}">
        <jsp:include page="contenus/user.jsp?login=${sessionScope.user.login}"/>
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