package server;

import java.util.Hashtable;
import servlet.ServletContext;
import servlet.http.HttpServlet;


public class MyServlet extends HttpServlet {


  private String servletName;

  private Hashtable<String, String> initParameter;

  public MyServlet(String servletName) {

    initParameter = new Hashtable<>();
  }

}
