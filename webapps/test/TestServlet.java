package test;

import java.io.IOException;
import java.io.PrintWriter;
import servlet.RequestDispatcher;
import servlet.http.HttpServlet;
import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;
import servlet.http.HttpSession;

public class TestServlet extends HttpServlet {

  public TestServlet() {

    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    // System.out.println(this.getInitParameter("color"));
    // System.out.println(this.getServletContext().getInitParameter("color"));
    // HttpSession session = request.getSession();

    // System.out.println(session.getAttribute("login"));
    // session.setAttribute("login", "aaa");
    // response.addCookie("abc", "test");
    // response.addCookie("abc2", "test2");
    RequestDispatcher dispatcher = request.getRequestDispatcher("/test/test.html");
    dispatcher.forward(request, response);

    // response.sendRedirect("test.html");
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html> <html> <head></head><body><h1>날라온 메시지 "
        + request.getParameter("lastname") + "</h1></body></html>");
    out.flush();
    out.close();

    // PrintWriter out = response.getWriter();
    // out.println("<h2> 성공</h2>");
    // out.flush();
    // out.close();
    // response.sendRedirect("success.html");
    // response.getWriter().println("ㅎㅎㅎ");
    // response.getWriter().flush();
    // response.getWriter().close();

  }

}
