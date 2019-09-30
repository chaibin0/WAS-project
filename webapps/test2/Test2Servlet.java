package test2;

import java.io.IOException;
import java.io.PrintWriter;
import servlet.http.HttpServlet;
import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;

public class Test2Servlet extends HttpServlet {

  public Test2Servlet() {

    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.setHeader("Content-type", "text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html> <html> <head></head><body><h1>하하하하</h1></body></html>");
    out.flush();
    out.close();

  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    doGet(request, response);
  }

}
