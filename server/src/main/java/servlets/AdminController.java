package servlets;

import classes.GestionPassages;
import classes.Passage;
import classes.Salle;
import classes.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


@WebServlet(name="admin", urlPatterns ="/admin")
public class AdminController extends HttpServlet {

    private ServletContext cntx;
    private GestionPassages gp;
    private HashMap<String, User> UsersHashMap;
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

        String contenu = request.getParameter("contenu");
        boolean Testcontent = (contenu == null || contenu.length() == 0);
        HttpSession session = request.getSession(true);
        User user=(User) session.getAttribute("user");
        boolean administrateur=user.getAdmin();
        String login= user.getLogin();




        if(!administrateur){
            request.getRequestDispatcher("interface.jsp").forward(request,response);
        }


        if (!Testcontent && administrateur && contenu.equals("salles")) {
            cntx = getServletContext();
            String nomSalle = request.getParameter("nomSalle");

        }





    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String contenu = request.getParameter("contenu");
        boolean Testcontent = (contenu == null || contenu.length() == 0);
        HttpSession session = request.getSession(true);
        User user=(User) session.getAttribute("user");
        boolean administrateur=user.getAdmin();
        String login= user.getLogin();


        if(!administrateur){
            request.getRequestDispatcher("interface.jsp").forward(request,response);
        }

        if (!Testcontent && administrateur && contenu.equals("passages")) {
            List<Passage> passages = gp.getAllPassages();
            request.setAttribute("passagesUser", passages);
            request.getRequestDispatcher("interface_admin.jsp?contenu=passages").forward(request,response);

        }


        if (!Testcontent && administrateur && contenu.equals("user_passage_form")) {
            request.getRequestDispatcher("interface_admin.jsp?contenu=user_form").forward(request,response);

        }

        if (!Testcontent && administrateur && contenu.equals("salle_passage_form")) {

            request.getRequestDispatcher("interface_admin.jsp?contenu=salle_passage_form").forward(request,response);

        }



        if (!Testcontent && administrateur && contenu.equals("user_form")) {

            request.getRequestDispatcher("interface_admin.jsp?contenu=user_form").forward(request,response);

        }





    }
}
