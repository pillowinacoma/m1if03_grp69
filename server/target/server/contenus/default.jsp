<%@ page import="classes.User" %>
<%@ page import="java.util.List" %>
<%@ page import="classes.Passage" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%

    List<Passage> passageList = (List<Passage>) request.getAttribute("passagesEnCours");
    User user = (User) request.getSession().getAttribute("user");
%>

<section id="contenu">
    <p><strong>Hello <%= user.getLogin() %> !</strong></p>
    <c:set var="myPassages" value="<%= passageList%>"/>
    <c:if test="${myPassages.size() > 0}">
        <p>Vous êtes actuellement dans les :</p>
        <ul>
            <c:forEach var="p" items="${myPassages}">
                <li>
                        ${p.salle.nom}
                    <c:if test="${p.salle.saturee}">
                        <strong style="color: red">Alerte : cette salle est saturée !</strong>
                    </c:if>
                </li>
            </c:forEach>
        </ul>
    </c:if>
    <p>Choisissez une option dans le menu.</p>
</section>
