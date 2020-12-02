package servlets;

import classes.GestionPassages;
import classes.Passage;
import classes.Salle;
import classes.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.util.*;

@WebServlet(name = "UsersController", urlPatterns = {"/users","/users/*"})
public class UsersController extends HttpServlet {

    GestionPassages passages;
    Map<String, User> users;
    Map<String, Salle> salles;

    @Override
    @SuppressWarnings("unchecked")
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.passages = (GestionPassages) config.getServletContext().getAttribute("passages");
        this.users = (Map<String, User>) config.getServletContext().getAttribute("users");
        this.salles = (Map<String, Salle>) config.getServletContext().getAttribute("salles");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String [] path=req.getRequestURI().split("/");

        int size=path.length;

        if(path.length!=0) {

            List<User> userList=new ArrayList<>();
            Set<String> keys = users.keySet();
            for(String key : keys){
                userList.add(users.get(key));
            }

            // ********* localhost:8080/users
            if (path.length!= 0 && path[size-1].equals("users")) {


                    String URL = getUrlDeBase(String.valueOf(req.getRequestURL()));
                    List<String> responseURL = new ArrayList<>();
                    for (User p : userList) {
                        responseURL.add(URL + "/users/" + p.getLogin());
                    }
                    Json_Object(responseURL, resp);

                    resp.setStatus(HttpServletResponse.SC_OK);

            }


            // ********** localhost:8080/users/{userid}

            if(path.length !=0 && path[size-2].equals("users")){

                    User  user = users.get(path[size-1]);
                    Json_Object(user,resp);
                    resp.setStatus(HttpServletResponse.SC_OK);



            }


            //************ localhost:8080/users/{userId}/passages
            if (path.length!=0 && path[size-3].equals("users") && path[size-1].equals("passages")) {

                        User userPassage = users.get(path[2]);
                        List<Passage> passagebyUser = passages.getPassagesByUser(userPassage);

                        String URL = getUrlDeBase(String.valueOf(req.getRequestURL()));
                        List<String> responseURL = new ArrayList<>();
                        for (Passage p : passagebyUser) {
                                responseURL.add(URL + "/passages/" + p.getId());
                        }
                        Json_Object(responseURL, resp);
                        resp.setStatus(HttpServletResponse.SC_SEE_OTHER);


            }





        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String [] path=req.getRequestURI().split("/");
        int size= path.length;

        if(path.length!=0 ){
            String var=path[size-1];
            if(var.equals("login")){
                JsonObject data =  payloadData(req);
                String loginuser = data.get("login").getAsString();
                User user=new User(loginuser);
                String nom=data.get("login").getAsString();
                Boolean admin=data.get("admin").getAsBoolean();
                user.setAdmin(admin);
                user.setNom(nom);

                if(users.containsKey(user)){

                }

            }else if (var.equals("logout")){
                req.getSession().invalidate();
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else{
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String [] path=req.getRequestURI().split("/");
        int size= path.length;

        if(size !=0){

            // users/{userid}/nom
            if(path[size-3].equals("users") && path[size-1].equals("nom") ){

                String login = path[size-2];
                JsonObject data =  payloadData(req);
                String nomUser = String.valueOf(data.get("nom").getAsString());



                if(users.containsKey(login)){
                    users.get(login).setNom(nomUser);
                }

                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);



            }
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
        public void Json_Object(Object value, HttpServletResponse response) throws IOException {
            Gson gson = new Gson();
            response.setContentType("application/json");
            String _json = gson.toJson(value);
            PrintWriter printWriter =  response.getWriter();

            printWriter.write(_json);
            printWriter.close();
        }

        public static String getUrlDeBase(String urlString) {
            if(urlString == null) { return null;}
            try {
                URL url = URI.create(urlString).toURL();
                return url.getProtocol() + "://" + url.getAuthority();
            } catch (Exception e) {
                return null;
            }
        }

        // renvoi l'objet JSON qui est dans le payload
        public JsonObject payloadData(HttpServletRequest request) throws IOException {
            // Read from request
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String paramIn = buffer.toString();

            if(paramIn.contains("=")){
                // recupere les données envoyé par un post (qui se trouve dans le body de la request)
                paramIn = paramIn.replaceAll("=", "\" : ");
                paramIn = paramIn.replaceAll("&", "\",\"");
                String result = "{\"" + paramIn + "\"}";;
                return new Gson().fromJson(result, JsonObject.class);
            }

            return new Gson().fromJson(buffer.toString(), JsonObject.class);

        }
}
