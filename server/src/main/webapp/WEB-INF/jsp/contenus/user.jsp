<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<jsp:useBean id="users" type="java.util.Map<java.lang.String,classes.User>" scope="application"/>
<c:set var="user" value="${users[param.login]}"/>

<section>
    <h1>User ${param.login}</h1>

    <c:if test="${user == null}">
        <h1>Utilisateur ${param.login} non trouvé</h1>
    </c:if>

    <c:if test="${user != null}">
        <ul>
            <li> Login : ${user.login}</li>
            <li>Nom : ${user.nom}</li>
            <li> admin : ${user.admin == true ? "oui" : "non"}</li>
        </ul>
    </c:if>
</section>