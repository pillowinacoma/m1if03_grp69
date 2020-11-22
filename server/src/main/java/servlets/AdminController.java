package  servlets;

import  classes.GestionPassages;
import  classes.Passage;
import  classes.Salle;
import  classes.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

@WebServlet(name = "AdminController", urlPatterns = {"/admin"})
public class AdminController extends HttpServlet {
    GestionPassages passages;
    Map<String, User> users;
    Map<String, Salle> salles;

    @Override
    @SuppressWarnings("unchecked")
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.passages = (GestionPassages) config.getServletContext().getAttribute("passages");
        this.users = (Map<String, User>) config.getServletContext().getAttribute("users");
        this.salles = (Map<String, Salle>) config.getServletContext().getAttribute("salles");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("contenu") != null) {
            Salle salle;
            switch (request.getParameter("contenu")) {
                case "salles":
                    if(request.getParameter("nomSalle") == null) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Le nom de la salle doit être précisé.");
                        return;
                    }
                    if(request.getParameter("action") == null) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "L'action sur la salle doit être précisée.");
                        return;
                    }
                    salle = salles.get(request.getParameter("nomSalle"));
                    if(salle == null
                            // On exclut le cas où on veut créer une nouvelle salle
                            && !(request.getParameter("action") != null && request.getParameter("action").equals("Ajouter"))) {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "La salle " + request.getParameter("nomSalle") + "n'existe pas.");
                        return;
                    }
                    switch (request.getParameter("action")) {
                        case "Modifier":
                            try {
                                salle.setCapacite(Integer.parseInt(request.getParameter("capacite")));
                            } catch (NumberFormatException e) {
                                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "La capacité d'une salle doit être un nombre entier.");
                                return;
                            }
                            break;
                        case "Supprimer":
                            salles.remove(salle.getNom());
                            break;
                        case "Ajouter":
                            salles.put(request.getParameter("nomSalle"), new Salle(request.getParameter("nomSalle")));
                            break;
                    }
            }
        }
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("contenu") != null) {
            switch (request.getParameter("contenu")) {
                case "passages":
                    if (request.getParameter("nomSalle") != null) {
                        if (request.getParameter("login") != null)
                            request.setAttribute("passagesAffiches", passages.getPassagesByUserAndSalle(new User(request.getParameter("login")), new Salle(request.getParameter("nomSalle"))));
                        else if (request.getParameter("dateEntree") != null && request.getParameter("dateSortie") != null) {
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", new Locale("us"));
                                Date dateEntree = sdf.parse(request.getParameter("dateEntree"));
                                Date dateSortie = sdf.parse(request.getParameter("dateSortie"));
                                request.setAttribute("passagesAffiches", passages.getPassagesBySalleAndDates(new Salle(request.getParameter("nomSalle")), dateEntree, dateSortie));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else
                            request.setAttribute("passagesAffiches", passages.getPassagesBySalle(new Salle(request.getParameter("nomSalle"))));
                    } else if (request.getParameter("login") != null)
                        request.setAttribute("passagesAffiches", passages.getPassagesByUser(new User(request.getParameter("login"))));
                    else
                        request.setAttribute("passagesAffiches", passages.getAllPassages());
                    break;
                case "passage":
                    try {
                        int id = Integer.parseInt(request.getParameter("num"));
                        request.setAttribute("passage", passages.getPassageById(id));
                    } catch(Exception e) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "numéro du passage non présent ou invalide" + e.getLocalizedMessage());
                        return;
                    }
                    break;
                case "salle":
                    request.setAttribute("salle", salles.get(request.getParameter("nomSalle")));
                    break;
                case "user":
                    request.setAttribute("user", users.get(request.getParameter("login")));
                    break;
            }
        } else {
            // Attribut de requête pour default
            request.setAttribute("mesPassages", passages.getPassagesByUserEncours((User) request.getSession().getAttribute("user")));
        }
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/interface_admin.jsp");
        dispatcher.include(request, response);
    }
}