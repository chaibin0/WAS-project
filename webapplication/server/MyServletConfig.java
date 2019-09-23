package server;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import servlet.ServletConfig;
import servlet.ServletContext;


public class MyServletConfig implements ServletConfig {

  private String servletName;

  private Map<String, String> initParameter;

  private ServletContext servletContext;

  public MyServletConfig(String servletName, Map<String, String> initParameter) {

    this.servletName = servletName;
    this.initParameter = Collections.unmodifiableMap(initParameter);
  }

  public MyServletConfig(String servletName) {

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
