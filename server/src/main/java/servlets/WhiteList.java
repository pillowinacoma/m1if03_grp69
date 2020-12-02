package servlets;

import java.util.ArrayList;
import java.util.List;

public class WhiteList {
    public static List<String>  getWhiteList(){

       List<String> whitelist=new ArrayList<>();
       whitelist.add("users/login");

       return whitelist;
    }

}
