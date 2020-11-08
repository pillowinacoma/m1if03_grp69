package servlets;


import classes.GestionPassages;
import classes.Passage;
import classes.Salle;
import classes.User;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@WebServlet(name = "presence",urlPatterns = "/presence")
public class PresenceController extends HttpServlet {

    private  ServletContext cntx;
     private GestionPassages gp;
     private  HashMap<String, User> UsersHashMap;
     private  HashMap<String, Salle> SallesHashMap;

    @Override
    public void init() throws ServletException {
        //
        ServletContext cntx= this.getServletContext();
          gp=(GestionPassages)cntx.getAttribute("gestionPassages");

     UsersHashMap = (HashMap<String, User>) cntx.getAttribute("Users");
     SallesHashMap = (HashMap<String, Salle>) cntx.getAttribute("Salles");


    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession sessionUser = request.getSession();
        User user=(User) sessionUser.getAttribute("user");


        List<Passage>  passageList=gp.getPassagesByUser(user);


    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String contenu = request.getParameter("contenu");
        boolean Testcontent = (contenu == null || contenu.length() == 0);
        HttpSession session = request.getSession(true);
        User user=(User) session.getAttribute("user");


        if (!Testcontent && contenu.equals("saisie_passage_form")) {
            request.getRequestDispatcher("interface.jsp?contenu=saisie_passage_form").forward(request,response);
        }

        if (Testcontent) {
            List<Passage> passagesEnCours = gp.getPassagesByUserEncours(user);
            request.setAttribute("passagesEnCours", passagesEnCours);
            request.setAttribute("user", user.getLogin());
            request.getRequestDispatcher("interface.jsp").forward(request,response);
        }


        if (!Testcontent  && contenu.equals("passagesUser")) {
            List<Passage> passagesEnCours = gp.getPassagesByUser(user);
            request.setAttribute("passagesUser", passagesEnCours);
            request.getRequestDispatcher("interface.jsp?contenu=passagesUser").forward(request,response);
        }

        if (!Testcontent && contenu.equals("saisie_passage")) {

            cntx = getServletContext();
            String nomSalle = request.getParameter("nom");

            Salle salle;

            if (!SallesHashMap.containsKey(nomSalle)) {
                salle = new Salle(nomSalle);
                SallesHashMap.put(nomSalle, salle);
            } else {
                salle = SallesHashMap.get(nomSalle);
            }

            if (request.getParameter("entree") != null) {

                if (!UsersHashMap.containsKey(user.getLogin())) {
                    UsersHashMap.put(user.getLogin(), user);
                }

                Passage p = new Passage(user, salle, new Date());
                gp.addPassage(p);
              cntx.setAttribute("gestionPassages", gp);
                salle.incPresent();

            } else if (request.getParameter("sortie") != null) {
                List<Passage> passTemp = gp.getPassagesByUserAndSalle(user, salle);
                for (Passage p : passTemp) { // On mémorise une sortie de tous les passages existants et sans sortie
                    if (p.getSortie() == null) {
                        p.setSortie(new Date());
                        salle.decPresent();
                    }
                }
            }
            response.setStatus(302);
            response.sendRedirect("presence");
        }
    }
}
