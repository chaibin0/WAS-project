package servlet;

import java.util.Enumeration;


public abstract class GenericServlet implements Servlet, ServletConfig, java.io.Serializable {

  private static final long serialVersionUID = 1L;

  private ServletConfig config;

  @Override
  public String getServletName() {

    return config.getServletName();
  }

  @Override
  public ServletContext getServletContext() {

    return config.getServletContext();
  }

  @Override
  public String getInitParameter(String name) {

    return config.getInitParameter(name);
  }

  @Override
  public Enumeration<String> getInitParameterNames() {

    return config.getInitParameterNames();
  }

  @Override
  public void init() {}

  public void init(ServletConfig config) {

    this.config = config;
    this.init();
  }

  @Override
  public void destory() {}

  @Override
  public void service() {

  }

}
