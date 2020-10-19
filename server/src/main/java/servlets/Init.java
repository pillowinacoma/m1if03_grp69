package servlets;


import classes.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebServlet(name = "Init", urlPatterns = "/Init")
public class Init extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        if(login != null && !login.equals("")) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", new User(login));
            session.setAttribute("admin", request.getParameter("admin") != null);
            request.getRequestDispatcher("passage.jsp").forward(request, response);
        } else {
            response.sendRedirect("index.html");
        }
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        response.sendRedirect("index.html");
    }

}
