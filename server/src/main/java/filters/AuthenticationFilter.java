package filters;

import classes.User;

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
        if((req.getSession(false) == null || req.getSession(false).getAttribute("user") == null)) {
            // Test d'une requête d'authentification
            if(req.getMethod().equals("POST")) {
                if(req.getParameter("action") != null && req.getParameter("action").equals("Connexion")
                        && req.getParameter("login") != null && !req.getParameter("login").equals("")) {
                    User user = new User(req.getParameter("login"));
                    user.setNom(req.getParameter("nom"));
                    user.setAdmin(req.getParameter("admin") != null && req.getParameter("admin").equals("on"));

                    // On ajoute l'user à la session
                    HttpSession session = req.getSession(true);
                    session.setAttribute("user", user);
                    // On rajoute l'user dans le DAO

                    users.put(req.getParameter("login"), user);
                } else {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
            } else {
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        chain.doFilter(req, resp);
    }
}