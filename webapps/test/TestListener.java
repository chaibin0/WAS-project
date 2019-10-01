package test;

import servlet.ServletContextEvent;
import servlet.ServletContextListener;

/**
 * Application Lifecycle Listener implementation class TestListener
 *
 */
public class TestListener implements ServletContextListener {

  /**
   * Default constructor.
   */
  public TestListener() {

    // TODO Auto-generated constructor stub
  }

  @Override
  public void contextInitialized(ServletContextEvent sce) {

    sce.getServletContext();
    System.out.println("서블릿 컨텍스트 리스너 테스트 초기화 완료");
  }

  

}
