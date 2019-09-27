package servlet;

import java.io.IOException;
import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;

public interface Filter {

  default public void init(FilterConfig filterConfig) {};

  public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException;

  default public void destroy() {}

}
