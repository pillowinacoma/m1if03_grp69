<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<section>
    <h1>Liste des salles</h1>
    <table>
        <tr>
            <th>Nom</th>
            <th>Capacité</th>
            <th>Nb présents</th>
        </tr>
        <%--
            Ici, on utilise le bean de scope application dans le contexte :
            Comme on a besoin de toutes les salles, inutile de le recopier en attribut de chaque requête envoyée à salles.jsp
        --%>
        <c:forEach items="${applicationScope.salles.entrySet()}" var="salleEntry">
            <tr>
                <td><a href="admin?contenu=salle&nomSalle=${salleEntry.value.nom}">${salleEntry.value.nom}</a></td>
                <td>
                    <form action="admin" method="post" accept-charset="utf-8">
                        <input type="text" name="capacite" size="3"
                               value="${salleEntry.value.capacite != -1 ? salleEntry.value.capacite : ''}"/>
                        <input type="hidden" name="contenu" value="salles">
                        <input type="hidden" name="nomSalle" value="${salleEntry.value.nom}">
                        <input type="submit" name="action" value="Modifier">
                    </form>
                </td>
                <td>${salleEntry.value.presents} présent(s)</td>
                <td>
                    <c:if test="${salleEntry.value.saturee}">
                        <strong style="color: red">Capacité dépassée</strong>
                    </c:if>
                </td>
                <td>
                    <form action="admin" method="post" accept-charset="UTF-8">
                        <input type="hidden" name="contenu" value="salles">
                        <input type="hidden" name="nomSalle" value="${salleEntry.value.nom}">
                        <input type="submit" name="action" value="Supprimer">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <hr>
    <h2>Ajouter une salle</h2>
    <form action="admin" method="post" accept-charset="UTF-8">
        <input type="hidden" name="contenu" value="salles">
        <label> Nom de la salle :
            <input type="text" name="nomSalle">
        </label>
        <input type="submit" name="action" value="Ajouter">
    </form>
</section>