package servlets;

import classes.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

//@WebFilter("/presence")
public class Filter extends HttpFilter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public  void doFilter(ServletRequest req, ServletResponse res, FilterChain filterchain) throws IOException,ServletException {
        //caster les objets req et res
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        //on récupère la session
        HttpSession session = request.getSession(true);
       boolean boolUser = session.getAttribute("user") != null;
        //on recupere l'URL
        String chemin=request.getRequestURI().substring(request.getContextPath().length());

       if(boolUser || chemin.startsWith("/index.jsp")){
            filterchain.doFilter(req,res);

        }else {
           if(request.getMethod().equals("POST")){

                   String login = request.getParameter("login");
                   if(login != null && !login.equals("")) {
                       User user = new User(login);
                       user.setNom(request.getParameter("nom"));
                       user.setAdmin(request.getParameter("admin") != null && request.getParameter("admin").equals("on"));
                       request.getSession().setAttribute("user", user);
                       response.addHeader("userlogin",  String.valueOf(session.getAttribute("user")));
                       response.sendRedirect("interface.jsp");
                   } else {
                       response.sendRedirect("index.jsp");
                   }
             }


        }

    }


    public void destroy(){}

}
