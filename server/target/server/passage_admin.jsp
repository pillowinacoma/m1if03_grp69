<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="classes.Passage" %>
<%@ page import="classes.GestionPassages" %>
<%@ page import="classes.Salle" %>
<%@ page import="java.util.Date" %>
<%@ page import="classes.User" %>
<%@ page import="java.util.List" %>


<jsp:useBean id="passages" scope="application" type="classes.GestionPassages" class="classes.GestionPassages"></jsp:useBean>


<% if (request.getMethod().equals("POST")) { // Traitement du formulaire envoyé par saisie.html

  if(request.getParameter("entree") != null) {
    passages.add(new Passage(
            (User) session.getAttribute("user"),
            new Salle(request.getParameter("nom")),
            new Date())
    );
  } else if(request.getParameter("sortie") != null) {
    List<Passage> passTemp = passages.getPassagesByUserAndSalle(
            (User) session.getAttribute("user"),
            new Salle(request.getParameter("nom"))
    );
    if(!passTemp.isEmpty()) {
      Passage p = passTemp.get(0);
      p.setSortie(new Date());
    }
  }
} %>

<!doctype html>
<html>
<head>
  <title>Passages</title>
</head>
<body>
<h2>Hello <%= ((User) (session.getAttribute("user"))).getLogin() %> !</h2>

<% List<Passage> passagesAffiches = null; %>


  <h1>Liste de tous les passages</h1>
  <% passagesAffiches = passages.getAllPassages(); %>


 f>

<table>
  <tr>
    <th>Login</th>
    <th>Salle</th>
    <th>Entrée</th>
    <th>Sortie</th>
  </tr>

  <c:forEach items="<%= passagesAffiches %>" var="passage">
    <tr>
      <td>${passage.user.login}</td>
      <td>${passage.salle.nom}</td>
      <td>
        <fmt:formatDate value="${passage.entree}" var="heureEntree" type="time" />
          ${heureEntree}
      </td>
      <td>
        <fmt:formatDate value="${passage.sortie}" var="heureSortie" type="time" />
          ${heureSortie}
      </td>
    </tr>
  </c:forEach>
</table>

<%--
 creation des parametres pour identifier
  --%>
<p><a href="saisie.html">Saisir un nouveau passage</a></p>
<p><a href="salle.jsp"> voir les salles </a></p>
<p><a href="user.jsp"> profil User </a></p>
<p><a href="Deco">Se déconnecter</a></p>

</body>
</html>