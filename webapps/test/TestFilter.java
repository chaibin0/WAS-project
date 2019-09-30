package test;

import java.io.IOException;
import servlet.FilterConfig;
import servlet.Filter;
import servlet.FilterChain;
import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class TestFilter
 */
public class TestFilter implements Filter {


  public TestFilter() {}

  public void destroy() {}

  public void init(FilterConfig fConfig) {}

  @Override
  public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException {
    
    System.out.println("startTestFilter!!");
    chain.doFilter(request, response);
    System.out.println("endTestFilter!!");

  }

}
