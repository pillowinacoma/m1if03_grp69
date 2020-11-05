package servlets;

import classes.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter("/*")
public class Filter extends HttpFilter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public  void doFilter(ServletRequest req, ServletResponse res, FilterChain filterchain) throws IOException,ServletException {
        //caster les objets req et res
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        //on récupère la session
       HttpSession sessionUser = request.getSession();

        //on recupere l'URL
        String chemin=request.getRequestURI().substring(request.getContextPath().length());




       if(sessionUser.getAttribute("user")!=null || chemin.startsWith("/index.jsp")){

            filterchain.doFilter(req,res);

        }else if(chemin.startsWith("/interface.jsp")){

           if(request.getMethod().equals("POST")){

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



           if(request.getMethod().equals("GET")){
               response.sendRedirect("index.jsp");
           }
        }else{

            response.sendRedirect("index.jsp");
        }
    }


    public void destroy(){}

}
