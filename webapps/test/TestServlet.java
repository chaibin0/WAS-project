package test;

import java.io.IOException;
import java.io.PrintWriter;
import servlet.RequestDispatcher;
import servlet.http.HttpServlet;
import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {

  public TestServlet() {

    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    // PrintWriter out = response.getWriter();
    // out.println("<!DOCTYPE html> <html> <head></head><body><h1>하하하하</h1></body></html>");
    // out.flush();
    // out.close();
    // RequestDispatcher dispatcher = request.getRequestDispatcher("/test2/test2");
    response.sendRedirect("test.html");
    // dispatcher.forward(request, response);

  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    System.out.println(request.getParameter("color"));
  }

}
