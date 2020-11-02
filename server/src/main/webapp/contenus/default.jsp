<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="passages" type="classes.GestionPassages" scope="application"/>

<section id="contenu">
    <p><strong>Hello ${sessionScope.user.nom} !</strong></p>
    <c:set var="myPassages" value="${passages.getPassagesByUserEncours(sessionScope.user)}"/>
    <c:if test="${myPassages.size() > 0}">
        <p>Vous êtes actuellement dans les salles :</p>
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
