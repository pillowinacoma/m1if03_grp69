<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>
<%@ page import="classes.Passage" %>
<%@ page import="classes.User" %>
<%@ page import="classes.Salle" %>

<jsp:useBean id="passages" type="classes.GestionPassages" scope="application"/>
<jsp:useBean id="salles" type="java.util.Map<java.lang.String, classes.Salle>"
             scope="application"/>

<c:set var="salle" value="${salles[param.nom]}"/>

<% // Gestion des POST
    if (request.getMethod().equals("POST")) { // Traitement du formulaire envoyé par saisie_passage.jsp
        @SuppressWarnings("unchecked")
        List<Passage> passagesAffiches = (List<Passage>) request.getAttribute("passagesAffiches");
        String nomSalle = request.getParameter("nom");
        Salle salle;
        if (salles.get(nomSalle) == null) {
            salle = new Salle(nomSalle);
            salles.put(nomSalle, salle);
        } else
            salle = salles.get(nomSalle);
        User user = (User) session.getAttribute("user");

        if (request.getParameter("entree") != null) {
            Passage p = new Passage(user, salle, new Date());
            passages.add(p);
            passagesAffiches.add(p); // On rajoute le passage dans passageAffiches qui arrive par un attribut de requête
            salle.incPresent();
        } else if (request.getParameter("sortie") != null) {
            List<Passage> passTemp = passages.getPassagesByUserAndSalle(user, salle);
            for (Passage p : passTemp) { // On mémorise une sortie de tous les passages existants et sans sortie
                if (p.getSortie() == null) {
                    p.setSortie(new Date());
                    salle.decPresent();
                }
            }
        }
    }

%>
<section>

    <c:if test="${salle.saturee}">
        <h2><span style="color: red">Alerte : capacite de la salle ${param.nom} dépassée</span></h2>
    </c:if>
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
            <th>Login</th>
            <th>Salle</th>
            <th>Entrée</th>
            <th>Sortie</th>
        </tr>
        <c:forEach items="${requestScope.passagesAffiches}" var="passage">
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