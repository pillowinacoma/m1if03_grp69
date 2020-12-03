package servlets;

import classes.GestionPassages;
import classes.Passage;
import classes.Salle;
import classes.User;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
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

@WebServlet(name = "SallesController", urlPatterns = {"/salles", "/salles/*"})
public class SallesController  extends HttpServlet {


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



         // Get uri
        String  path [] =req.getRequestURI().split("/");
        int size = path.length;


        if(path.length !=0 ){
            List<Salle> salleList=new ArrayList<>();
            Set<String> keys = salles.keySet();
            for(String key : keys){
                salleList.add(salles.get(key));
            }

            /* salles */
            if(path.length !=0 &&  path[size-1].equals("salles") ){


                    String URL = getUrlDeBase(String.valueOf(req.getRequestURL()));
                    List<String> responseURL = new ArrayList<>();
                    for (Salle salle : salleList){
                        responseURL.add(URL+"/salles/"+ salle.getNom());
                    }
                    Json_Object(responseURL, resp);

                    resp.setStatus(HttpServletResponse.SC_OK);



            }

            // *************  /salles/{salleID}
            if(path.length!=0 && path[size-2].equals("salles")){

                    String salleName = path[size-1];
                    Salle salle =  salles.get(salleName);
                    Json_Object(salle, resp);
                    resp.setStatus(HttpServletResponse.SC_OK);


            }

            //********** /salles/{salleID}/passages
            //renvoie uri des passages dans la salle specifiée

            if (path.length!=0 && path[size-3].equals("salles") && path[size-1].equals("passages") ) {

                        Salle sallePassage =salles.get(path[size-2]);
                        List<Passage> passagebysalle = passages.getPassagesBySalle(sallePassage);

                        String URL = getUrlDeBase(String.valueOf(req.getRequestURL()));
                        List<String> responseURL = new ArrayList<>();
                        for (Passage p : passagebysalle) {
                            if (p.getSalle().getNom().equals(sallePassage.getNom())) {
                                responseURL.add(URL + "/passages/" + p.getId());
                            }
                        }
                        Json_Object(responseURL, resp);
                        resp.setStatus(HttpServletResponse.SC_SEE_OTHER);


            }else if((path[size-3].equals("salles") && !path[size-1].equals("passages")) || (!path[size-3].equals("salles") && !path[size-1].equals("passages")) ){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }



        }





    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String [] path=req.getRequestURI().split("/");
        int size=path.length;

        if(path.length != 0) {

           //  /salles
            if (path.length!=0 && path[size-1].equals("salles")) {

                    JsonObject data =  payloadData(req);
                    String salleName = data.get("nomSalle").getAsString();
                    Salle salle = new Salle(salleName);
                    if(!salles.containsKey(salleName)){
                        salles.put(salleName, salle);
                        resp.setStatus(HttpServletResponse.SC_CREATED);
                        System.out.println("sallecree");
                    }
                    Json_Object(salle,resp);


            }else{
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String [] path=req.getRequestURI().split("/");
        int size=path.length;
        if(path.length !=0) {
            /* /salles/{salleID}*/
            if(path.length!=0 && path[size-2].equals("salles")){

                    String salleName = path[size-1];
                    JsonObject data =  payloadData(req);
                    String sallePayload = String.valueOf(data.get("nomSalle").getAsString());

                    int capacite = Integer.parseInt(String.valueOf(data.get("capacite")));

                    if(salles.containsKey(sallePayload) && salleName.equals(sallePayload)){
                         salles.get(salleName).setCapacite(capacite);
                    }

                    if(!salles.containsKey(sallePayload)){
                        Salle salle=new Salle(sallePayload);
                        salles.put(sallePayload,salle);
                    }

                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);



            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String [] path=req.getRequestURI().split("/");
        int size=path.length;

        if(path.length!=0){
            if(path.length!=0 && path[size-2].equals("salles")){

                    String salleName=path[size-1];

                    for (Map.Entry<String,Salle> entry : salles.entrySet()){
                        if(entry.getKey().equals(salleName)){

                            salles.remove(entry.getKey());

                        }

                    }

                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);

            }
        }
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
