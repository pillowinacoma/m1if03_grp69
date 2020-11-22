<%@ page contentType="text/html; charset=UTF-8" %>
<section>
    <h1>Passages dans une salle</h1>

    <form method="get" action="interface_admin.jsp">
        <label>
            Nom de la salle cherchée :
            <input type="text" name="nomSalle" autofocus>
        </label>
        <input type="hidden" name="contenu" value="passages">
        <input type="submit" value="Envoyer">
    </form>
</section>