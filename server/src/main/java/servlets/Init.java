package servlets;


import classes.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebServlet(name = "Init", urlPatterns = "/Init", loadOnStartup = 1)
public class Init extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        if(login != null && !login.equals("")) {
            User user = new User(login);
            user.setNom(request.getParameter("nom"));
            user.setAdmin(request.getParameter("admin") != null && request.getParameter("admin").equals("on"));
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            response.sendRedirect("interface.jsp");
        } else {
            response.sendRedirect("index.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("index.jsp");
    }
}
