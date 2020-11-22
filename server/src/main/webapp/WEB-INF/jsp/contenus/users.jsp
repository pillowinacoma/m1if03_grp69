<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section>
    <h1>Liste des utilisateurs</h1>
    <table>
        <tr>
            <th>Login</th>
            <th>Nom</th>
            <th>admin</th>
        </tr>
        <%--
            Ici, on utilise le bean de scope application dans le contexte :
            Comme on a besoin de toutes les salles, inutile de le recopier en attribut de chaque requête envoyée à salles.jsp
        --%>
        <c:forEach items="${applicationScope.users.entrySet()}" var="userEntry">
            <tr>
                <td><a href="admin?contenu=user&login=${userEntry.value.login}">${userEntry.value.login}</a></td>
                <td>${userEntry.value.nom}</td>
                <td>${userEntry.value.admin == true ? "oui" : "non"}</td>
            </tr>
        </c:forEach>
    </table>
</section>