package servlets;

import classes.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/interface_admin.jsp")
public class AdminFilter  extends HttpFilter {
    @Override
    public void init(FilterConfig config) throws ServletException{}


    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,ServletException {
        //caster les objets req et res
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession sessionUser = request.getSession();
        User user=(User) sessionUser.getAttribute("user");



        if(sessionUser.getAttribute("user")!=null) {

            if (user.getAdmin() == false) {
                response.sendRedirect("interface.jsp");
            }else{
                request.getRequestDispatcher("/interface_admin.jsp").forward(request,response);
            }

        }else{
            response.sendRedirect("index.jsp");
        }
    }


        @Override
    public void destroy() {}
}
