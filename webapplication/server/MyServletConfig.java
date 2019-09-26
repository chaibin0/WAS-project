package server;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import servlet.ServletConfig;
import servlet.ServletContext;


/**
 * ServletConfig 인터페이스를 구현한 클래스이다.
 */
public class MyServletConfig implements ServletConfig {

  private String servletName;

  private Map<String, String> initParameter;

  private Container container;

  private ServletContext servletContext;

  /**
   * initParameter가 존재할 경우 객체를 생성한다.
   * 
   * @param servletName 서블릿이름
   * @param initParameter InitParameter
   */
  public MyServletConfig(String servletName, Map<String, String> initParameter) {

    container = Container.getInstance();
    servletContext = container.getServletContext();
    this.servletName = servletName;
    this.initParameter = Collections.unmodifiableMap(initParameter);
  }

  /**
   * initParameter가 존재하지 않을 경우 객체를 생성한다.
   * 
   * @param servletName 서블릿 이름
   */
  public MyServletConfig(String servletName) {

    container = Container.getInstance();
    servletContext = container.getServletContext();
    this.servletName = servletName;
    this.initParameter = new HashMap<>();

  }

  @Override
  public String getServletName() {

    return servletName;
  }

  @Override
  public ServletContext getServletContext() {

    return servletContext;
  }

  @Override
  public String getInitParameter(String name) {

    return initParameter.get(name);
  }

  @Override
  public Enumeration<String> getInitParameterNames() {

    return null;
  }

}
