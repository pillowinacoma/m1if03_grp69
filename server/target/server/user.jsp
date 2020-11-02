<%--
  Created by IntelliJ IDEA.
  User: mahmoud
  Date: 31/10/2020
  Time: 08:54
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.Passage" %>
<%@ page import="classes.GestionPassages" %>
<%@ page import="classes.Salle" %>
<%@ page import="java.util.Date" %>
<%@ page import="classes.User" %>
<%@ page import="java.util.List" %>

<%-- déclaration du java bean qui est aussi déclaré pareillement dans passges.jsp --%>
<jsp:useBean id="passages" scope="application" type="classes.GestionPassages" ></jsp:useBean>


<html>
  <head>
    <title>User</title>
  </head>
  <body>

  <% List<Passage> infosUser = null; %>


  <c:if test="${sessionScope.admin}">
    <h1>Informations sur les utilisateurs</h1>
    <% infosUser = passages.getAllPassages(); %>
  </c:if>


  <%--
   // Dans ce cas nous gérons le fait que seul l'admin a le droit de voir les informations sur n'importe quel utilisateur
  // si on est n'est pas identifié en tant que admin on nous renvoie à la  saisie des informations.
--%>


  <c:if test="${!sessionScope.admin}">
    <jsp:forward page="saisie.html" />
  </c:if>


  <table>
    <tr>
      <th>Profil Utilisateurs</th>
    </tr>

    <c:forEach items="<%= infosUser %>" var="passage">
      <tr>

        <td>${passage.user.login}</td>
      </tr>
    </c:forEach>
  </table>
  
  </body>
</html>
