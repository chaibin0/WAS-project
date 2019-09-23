package servlet.http;

import java.io.IOException;
import servlet.GenericServlet;
import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;

public class HttpServlet extends GenericServlet {

  private static final long serialVersionUID = 1L;

  public HttpServlet() {}

  public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {

    String method = request.getMethod();

    switch (method) {
      case "GET":
        doGet(request, response);
        break;
      case "POST":
        doPost(request, response);
        break;
      case "PUT":
        
        break;
      case "DELETE":
        break;
      case "CONNECT":
        break;
      case "TRACE":
        break;
      default:
        doGet(request, response);
    }


  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {}

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {}

}
