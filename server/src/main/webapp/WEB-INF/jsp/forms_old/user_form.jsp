<%@ page contentType="text/html; charset=UTF-8" %>
<section>
    <h1>Affichage d'un utilisateur</h1>

    <form method="get" action="interface_admin.jsp">
        <label>
            Login de l'utilisateur cherché :
            <input type="text" name="login" autofocus>
        </label>
        <input type="hidden" name="contenu" value="user">
        <input type="submit" value="Envoyer">
    </form>
</section>