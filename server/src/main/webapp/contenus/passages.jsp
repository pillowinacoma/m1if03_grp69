<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>
<%@ page import="classes.Passage" %>
<%@ page import="classes.User" %>
<%@ page import="classes.Salle" %>

<%

    List<Passage> passagesUser = (List<Passage>) request.getAttribute("passagesUser");

%>


<section>

   <h1>
       LISTE DES PASSAGES
    </h1>

    <table>
        <tr>
            <th>Login</th>
            <th>Salle</th>
            <th>Entrée</th>
            <th>Sortie</th>
        </tr>
        <c:forEach items="<%= passagesUser %>" var="passage">
            <tr>
                <td>
                    <a href="<%= request.getServletPath().substring(1) %>?contenu=passages&login=${passage.user.login}">${passage.user.login}</a>
                </td>
                <td>
                    <a href="<%= request.getServletPath().substring(1) %>?contenu=passages&nomSalle=${passage.salle.nom}">${passage.salle.nom}</a>
                </td>
                <td>
                    <fmt:formatDate value="${passage.entree}" var="heureEntree" type="time"/>
                        ${heureEntree}
                </td>
                <td>
                    <fmt:formatDate value="${passage.sortie}" var="heureSortie" type="time"/>
                        ${heureSortie}
                </td>
                <c:if test="${passage.sortie != null && sessionScope.user.admin}">
                    <td>
                        <a href="interface_admin.jsp?contenu=passages&nomSalle=${passage.salle.nom}&dateEntree=${passage.entree}&dateSortie=${passage.sortie}">tous
                            les présents</a>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</section>
