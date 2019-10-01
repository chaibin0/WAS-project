package test;

import java.io.IOException;
import servlet.Filter;
import servlet.FilterChain;
import servlet.FilterConfig;
import servlet.ServletRequest;
import servlet.ServletResponse;

/**
 * Servlet Filter implementation class TestFilter
 */
public class TestFilter implements Filter {


  public TestFilter() {}

  public void destroy() {}

  public void init(FilterConfig fConfig) {}

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException {
    
    System.out.println("startTestFilter!!");
    chain.doFilter(request, response);
    System.out.println("endTestFilter!!");

  }

}
