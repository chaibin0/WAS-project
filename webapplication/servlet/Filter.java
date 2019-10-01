package servlet;

import java.io.IOException;

public interface Filter {

  default public void init(FilterConfig filterConfig) {};

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException;

  default public void destroy() {}

}
