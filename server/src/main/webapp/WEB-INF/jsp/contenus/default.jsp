<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<jsp:useBean id="passages" type="classes.GestionPassages" scope="application"/>

<section id="contenu">
    <h1>Hello ${sessionScope.user.nom} !</h1>

    <c:set var="myPassages" value="${passages.getPassagesByUserEncours(sessionScope.user)}"/>
    <c:if test="${myPassages.size() > 0}">
        <h2>Vous êtes actuellement dans les salles :</h2>
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
    <hr>
    <h2>Saisie d'un passage dans une salle</h2>
    <p>Indiquez le nom d'une salle, et cliquez sur l'un des boutons pour indiquer que vous y entrez ou que vous en
        sortez.</p>
    <form method="post" action="presence">
        <p>
            <label>
                Nom de la salle :
                <input type="text" name="nomSalle" autofocus>
            </label>
        </p>
        <p>
            <input type="submit" name="action" value="Entrer">
            <input type="submit" name="action" value="Sortir">
        </p>
    </form>
</section>