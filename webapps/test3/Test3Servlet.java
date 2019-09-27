package test3;

import java.io.IOException;
import java.io.PrintWriter;
import servlet.http.HttpServlet;
import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;

public class Test3Servlet extends HttpServlet {

  public Test3Servlet() {

    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html> <html> <head></head><body><h1>war</h1></body></html>");
    out.flush();
    out.close();

  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    doGet(request, response);
  }

}
