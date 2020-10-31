<%--
  Created by IntelliJ IDEA.
  User: mahmoud
  Date: 19/10/2020
  Time: 14:29
  To change this template use File | Settings | File Templates.
  <h2>Hello <%= ((User) (session.getAttribute("user"))).getLogin() %> !</h2>
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="classes.Passage" %>
<%@ page import="classes.GestionPassages" %>
<%@ page import="classes.Salle" %>
<%@ page import="java.util.Date" %>
<%@ page import="classes.User" %>
<%@ page import="java.util.List" %>

<%-- déclaration du java bean qui est aussi déclaré pareillement dans passges.jsp --%>
<jsp:useBean id="passages" scope="application" type="classes.GestionPassages" ></jsp:useBean>


<!doctype html>
<html>
<head>
    <title>Salles</title>
</head>
<body>
 <% List<Passage> sallesAffichees = null; %>

 <c:if test="${sessionScope.admin}">
     <h1>Liste de toutes les salles</h1>
     <% sallesAffichees = passages.getAllPassages(); %>
 </c:if>

 <c:if test="${!sessionScope.admin}">
     <h1>Historique de vos salles</h1>
     <% sallesAffichees = passages.getPassagesByUser((User) session.getAttribute("user")); %>
 </c:if>

 <table>
     <tr>
         <th>Salles</th>
     </tr>

     <c:forEach items="<%= sallesAffichees %>" var="passage">
         <tr>

             <td>${passage.salle.nom}</td>
         </tr>
     </c:forEach>
 </table>



</body>
</html>
