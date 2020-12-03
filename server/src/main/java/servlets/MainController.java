package servlets;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet(name = "MainController", urlPatterns = "/presence")
public class MainController extends HttpServlet {
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
       /* HttpSession session = request.getSession();

        if (request.getParameter("action") == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action non spécifiée.");
            return;
        }
        // Si le paramètre "action" est "Connexion", la requête est traitée dans le filtre, il n'y a rien à faire.
        if (!request.getParameter("action").equals("Connexion")) {
            String nomSalle = request.getParameter("nomSalle");
            if (nomSalle == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Salle non spécifiée.");
                return;
            }
            Salle salle = salles.get(nomSalle);
            if (salle == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "La salle " + nomSalle + " n'existe pas.");
                return;
            }

            User user = (User) session.getAttribute("user");

            if (request.getParameter("action").equals("Entrer")) {
                Passage p = new Passage(user, salle, new Date());
                passages.add(p);
                salle.incPresent();
            } else if (request.getParameter("action").equals("Sortir")) {
                List<Passage> passTemp = passages.getPassagesByUserAndSalle(user, salle);
                for (Passage p : passTemp) { // On mémorise une sortie de tous les passages existants et sans sortie
                    if (p.getSortie() == null) {
                        p.setSortie(new Date());
                        salle.decPresent();
                    }
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action " + request.getParameter("action") + " invalide.");
                return;
            }
        }
        doGet(request, response);*/
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      /*  HttpSession session = request.getSession();
        if (request.getParameter("contenu") != null) {
            switch (request.getParameter("contenu")) {
                case "passages":
                    if (request.getParameter("nomSalle") != null)
                        request.setAttribute("passagesAffiches", passages.getPassagesByUserAndSalle((User) session.getAttribute("user"), new Salle(request.getParameter("nomSalle"))));
                    else
                        request.setAttribute("passagesAffiches", passages.getPassagesByUser((User) session.getAttribute("user")));
                    break;
                case "passage":
                    try {
                        int id = Integer.parseInt(request.getParameter("num"));
                        Passage passage = passages.getPassageById(id);
                        if (!passage.getUser().equals(session.getAttribute("user"))) {
                            response.sendError(HttpServletResponse.SC_FORBIDDEN);
                            return;
                        }
                        request.setAttribute("passage", passages.getPassageById(id));
                    } catch (Exception e) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "numéro du passage non présent ou invalide");
                        return;
                    }
                    break;
                case "user":
                    if (request.getParameter("login") != null && !request.getParameter("login").equals(((User) (session.getAttribute("user"))).getLogin())) {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    } else if (session.getAttribute("user") == null) {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }
                    request.setAttribute("user", session.getAttribute("user"));
                    break;
            }
        } else {
            // Attribut de requête pour default
            request.setAttribute("mesPassages", passages.getPassagesByUserEncours((User) session.getAttribute("user")));
            response.setHeader("Refresh", "5");
        }
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/interface.jsp");
        dispatcher.include(request, response);
    }
    */
    }
}