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
import javax.validation.constraints.Null;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static servlets.SallesController.getUrlDeBase;


@WebServlet(name = "PassagesController", urlPatterns = {"/passages","/passages/*"})
public class PassagesController extends HttpServlet {

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
        String [] path=req.getRequestURI().split("/");
        int size=path.length;
        if(path.length!=0){

            // /passages
            if(path.length!=0 && path[size-1].equals("passages") ){

                    List<Passage> passageAll=passages.getAllPassages();

                    String URL = getUrlDeBase(String.valueOf(req.getRequestURL()));
                    List<String> responseURL = new ArrayList<>();
                    for (Passage  p : passageAll){
                        responseURL.add(URL+"/passages/"+ p.getId());
                    }
                    Json_Object(responseURL, resp);

                    resp.setStatus(HttpServletResponse.SC_OK);

            }


            //**********  /passages/{id}
            //liste des passages par l'identifiant
            if(path.length!=0 && path[size-2].equals("passages") ){

                    String idPassage=path[size-1];

                    Integer id=Integer.parseInt(idPassage);
                    Passage passagebyid= passages.getPassageById(id);

                    Json_Object(passagebyid,resp);
                    resp.setStatus(HttpServletResponse.SC_OK);

            }


            //*********** localhost:8080/passages/byUser/{userId}

            if(path.length!=0 && path[size-3].equals("passages") && path[size-2].equals("byUser")){

                        String passagebyuser=path[3];
                        User user=users.get(passagebyuser);




                        List<Passage> passagebyuserList=passages.getPassagesByUser(user);
                        String URL = getUrlDeBase(String.valueOf(req.getRequestURL()));
                        List<String> responseURL = new ArrayList<>();

                        for (Passage  p : passagebyuserList){
                            responseURL.add(URL+"/passages/"+ p.getId());
                        }

                        Json_Object(responseURL,resp);
                        resp.setStatus(HttpServletResponse.SC_OK);


            }
            //********** localhost:8080/passages/byUserAndSalle/{userId}/{salleId}

            if(path.length!=0 && path[size-3].equals("byUserAndSalle") ) {

                String userId = path[size - 2];
                String salleId = path[size - 1];
                User userbyUserbySalle = users.get(userId);
                Salle sallebyUserbySalle = salles.get(salleId);

                List<Passage> passagebyUserbySalleList = passages.getPassagesByUserAndSalle(userbyUserbySalle, sallebyUserbySalle);
                String URL = getUrlDeBase(String.valueOf(req.getRequestURL()));
                List<String> responseURL = new ArrayList<>();
                for (Passage p : passagebyUserbySalleList) {
                    if (p.getSalle().getNom().equals(sallebyUserbySalle.getNom()) && p.getUser().getLogin().equals(userbyUserbySalle.getLogin())) {

                        responseURL.add(URL + "/passages/" + p.getId());
                    }
                }

                Json_Object(responseURL, resp);
                resp.setStatus(HttpServletResponse.SC_OK);
            }

            //*********** localhost:8080/passages/byUser/{userId}/enCours

            if(path[2].equals("byUser") && path[4].equals("enCours")){
                String userId=path[size-2];

                User userbyUserenCours=users.get(userId);

                List<Passage> passageUser=passages.getPassagesByUser(userbyUserenCours);
                List<Passage> passageUserenCours=new ArrayList<>();
                String URL = getUrlDeBase(String.valueOf(req.getRequestURL()));
                List<String> responseURL = new ArrayList<>();


                for(Passage p:passageUser ) {
                    if (p.getSortie()==null){
                            //System.out.println(p.getSortie());
                        passageUserenCours.add(p);
                    }

                }

                for(Passage p:passageUserenCours){
                    responseURL.add(URL + "/passages/" + p.getId());
                }

                Json_Object(responseURL,resp);
                resp.setStatus(HttpServletResponse.SC_OK);


            }


        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String [] path=req.getRequestURI().split("/");

        if(path.length > 0) {

            //  /salles
            if (path.length == 2) {
                if (path[1].equals("passages")) {
                    JsonObject data =  payloadData(req);
                    String sallepayload = data.get("salle").getAsString();
                    String userpayload =data.get("user").getAsString();
                    String dataentrepaylod=data.get("dateEntree").getAsString();
                    String datasortiepayload=data.get("dateSortie").getAsString();
                    Date dateEntree=new Date();
                    Date dateSortie=new Date();

                    try {
                        dateEntree=new SimpleDateFormat("dd/MM/yyyy").parse(dataentrepaylod);
                        dateSortie=new SimpleDateFormat("dd/MM/yyyy").parse(datasortiepayload);
                    } catch (ParseException e) {

                    }


                    User user=new User(userpayload);
                    Salle salle = new Salle(sallepayload);

                    int idndice=0;


                    Passage passagepayload=new Passage(user,salle,dateEntree);


                    passages.add(passagepayload);
                    for(Passage p:passages.getAllPassages()) {
                        if (p == passagepayload) {
                            p.setSortie(dateSortie);
                        }
                    }

                    resp.setStatus(HttpServletResponse.SC_CREATED);


                }
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


