package test;

import java.io.IOException;
import servlet.Filter;
import servlet.FilterChain;
import servlet.FilterConfig;
import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class TestFilter
 */
public class TestFilter2 implements Filter {


  public TestFilter2() {}

  public void destroy() {}

  public void init(FilterConfig fConfig) {}

  @Override
  public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException {

    System.out.println("startTestFilter2");
    chain.doFilter(request, response);
    System.out.println("endTestFilter2");

  }

}
