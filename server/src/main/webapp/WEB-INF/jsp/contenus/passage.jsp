<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<jsp:useBean id="passage" type="fr.univlyon1.m1if.m1if03.classes.Passage" scope="request"/>

<section>
    <h1>Passage numéro ${passage.id}</h1>

    <ul>
        <li>Id : ${passage.id}</li>
        <li>User : <a
                href="<%= request.getServletPath().substring(1) %>?contenu=passages&login=${passage.user.login}">${passage.user.login}</a>
        </li>
        <li>Salle : <a
                href="<%= request.getServletPath().substring(1) %>?contenu=passages&nomSalle=${passage.salle.nom}">${passage.salle.nom}</a>
        </li>
        <li>Entrée : <fmt:formatDate value="${passage.entree}" var="heureEntree" type="time"/> ${heureEntree}</li>
        <li>Sortie : <fmt:formatDate value="${passage.sortie}" var="heureSortie" type="time"/> ${heureSortie}</li>
    </ul>
    <c:if test="${passage.sortie != null && sessionScope.user.admin}">
        <p>
            <a href="admin?contenu=passages&nomSalle=${passage.salle.nom}&dateEntree=${passage.entree}&dateSortie=${passage.sortie}">
                tous les présents pendant ce passage
            </a>
        </p>
    </c:if>
</section>