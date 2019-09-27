package servlet;

import java.io.IOException;
import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;

public interface FilterChain {

  public void doFilter(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
