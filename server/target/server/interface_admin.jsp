<%@ page import="classes.User" %>
<%@ page import="classes.Salle" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.ParseException" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page errorPage="erreurs/error.jsp" %>

<c:if test="${!sessionScope.user.admin}">
  <% response.sendRedirect("interface.jsp"); %>
</c:if>

<jsp:useBean id="passages" type="classes.GestionPassages" scope="application"/>

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
      <c:when test="${param.contenu == \"passages\"}">
        <%
          if (request.getParameter("nomSalle") != null) {
            if (request.getParameter("login") != null)
              request.setAttribute("passagesAffiches", passages.getPassagesByUserAndSalle(new User(request.getParameter("login")), new Salle(request.getParameter("nomSalle"))));
            else if (request.getParameter("dateEntree") != null && request.getParameter("dateSortie") != null) {
              try {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", new Locale("us"));
                Date dateEntree = sdf.parse(request.getParameter("dateEntree"));
                Date dateSortie = sdf.parse(request.getParameter("dateSortie"));
                request.setAttribute("passagesAffiches", passages.getPassagesBySalleAndDates(new Salle(request.getParameter("nomSalle")), dateEntree, dateSortie));
              } catch (ParseException e) {
                e.printStackTrace();
              }
            } else
              request.setAttribute("passagesAffiches", passages.getPassagesBySalle(new Salle(request.getParameter("nomSalle"))));
          } else if (request.getParameter("login") != null)
            request.setAttribute("passagesAffiches", passages.getPassagesByUser(new User(request.getParameter("login"))));
          else
            request.setAttribute("passagesAffiches", passages.getAllPassages());
        %>
        <jsp:include page="contenus/passages.jsp"/>
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