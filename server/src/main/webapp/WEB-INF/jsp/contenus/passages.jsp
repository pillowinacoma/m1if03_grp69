<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<section>
    <h1>
        Liste des passages
        <c:if test="${param.login != null}">
            pour l'utilisateur ${param.login}
        </c:if>
        <c:if test="${param.nomSalle != null}">
            dans la salle ${param.nomSalle}
        </c:if>
        <c:if test="${param.dateSortie != null}">
            <%
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", new Locale("us"));
                    Date dateEntree = sdf.parse(request.getParameter("dateEntree"));
                    Date dateSortie = sdf.parse(request.getParameter("dateSortie"));
            %>
            entre
            <fmt:formatDate value="<%= dateEntree %>" var="dateEntree" type="time"/>
            ${dateEntree}
            et
            <fmt:formatDate value="<%= dateSortie %>" var="dateSortie" type="time"/>
            ${dateSortie}
            <%
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            %>
        </c:if>
    </h1>

    <table>
        <tr>
            <th>Numéro</th>
            <th>Login</th>
            <th>Salle</th>
            <th>Entrée</th>
            <th>Sortie</th>
        </tr>
        <c:forEach items="${requestScope.passagesAffiches}" var="passage">
            <tr>
                <td><a href="<%= request.getServletPath().substring(1) %>?contenu=passage&num=${passage.id}">${passage.id}</a></td>
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
                        <a href="admin?contenu=passages&nomSalle=${passage.salle.nom}&dateEntree=${passage.entree}&dateSortie=${passage.sortie}">tous
                            les présents</a>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</section>