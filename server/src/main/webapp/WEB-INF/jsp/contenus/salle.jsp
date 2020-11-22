<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<c:set var="user" value="${requestScope.salle}"/>

<section>
    <h1>Salle ${param.nomSalle}
    <c:if test="${user == null}">
        non trouvée
    </c:if>
    </h1>

    <c:if test="${user != null}">
        <ul>
            <li>Nom : ${user.nom}</li>
            <li>Capacité : ${user.capacite == -1 ? "Non spécifiée" : user.capacite}</li>
            <li>Nb présents : ${user.presents}</li>
            <li>Saturée : ${user.saturee ? "oui" : "non"}</li>
        </ul>
    </c:if>
</section>