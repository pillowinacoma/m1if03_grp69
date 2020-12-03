package filters;

import  classes.User;
import utils.PresenceUcblJwtHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AuthorizationFilter")
public class AuthorizationFilter extends HttpFilter {
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {
        PresenceUcblJwtHelper presenceUcblJwtHelper = new PresenceUcblJwtHelper();
        String uriget = req.getRequestURI().substring(req.getContextPath().length());


        if( uriget.equals( "/users/login")){
            chain.doFilter(req, resp);
        } else {
            String token = req.getHeader("Authorization");
            boolean admin = presenceUcblJwtHelper.verifyAdmin(token);
            if(admin){
                chain.doFilter(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        }
    }
}