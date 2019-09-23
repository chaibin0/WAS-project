package servlet;

import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;

public interface RequestDispatcher {

  void forward(HttpServletRequest request, HttpServletResponse response);
}
