package filters;

import classes.User;
import utils.PresenceUcblJwtHelper;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebFilter(filterName = "AuthenticationFilter")
public class AuthenticationFilter extends HttpFilter {
    Map<String, User> users;

    @SuppressWarnings("unchecked")
    public void init(FilterConfig config) throws ServletException {
        super.init(config);
        this.users = (Map<String, User>) config.getServletContext().getAttribute("users");
    }

    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {
        // Test des URLs qui nécessitent une autorisation

        // Test des URLs qui nécessitent une autorisation
        PresenceUcblJwtHelper presenceUcblJwtHelper = new PresenceUcblJwtHelper();

        String tokenuser = req.getHeader("Authorization");
        String loginuser  = presenceUcblJwtHelper.verifyToken(tokenuser,req);

        //test sur le login recuperer
        if(loginuser != null){
            req.setAttribute("token", tokenuser);
            chain.doFilter(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            resp.sendRedirect("./");
        }
    }


}