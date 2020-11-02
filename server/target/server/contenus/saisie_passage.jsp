<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<section>
    <h1>Passage dans une salle</h1>
    <p>Indiquez le nom d'une salle, et cliquez sur l'un des boutons pour indiquer que vous y entrez ou que vous en
        sortez.</p>
    <form method="post" action="interface.jsp">
        <input type="hidden" name="contenu" value="passages">
        <p>
            <label>
                Nom de la salle :
                <input type="text" name="nom" autofocus>
            </label>
        </p>
        <p>
            <input type="submit" value="Entrée" name="entree">
            <input type="submit" value="Sortie" name="sortie">
        </p>
    </form>
</section>