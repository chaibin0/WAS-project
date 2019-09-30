package server;

import java.util.HashMap;
import java.util.Map;
import servlet.http.HttpServlet;

/**
 * Web.xml을 통해 분석된 서블릿 정보를 저장하는 클래스ㄹ.
 */
class MappingServlet extends HttpServlet {

  private static final long serialVersionUID = 5142307503305479381L;

  private int loadOnStartup;

  private String description;

  private String servletClassName;

  private Map<String, String> initParameter;

  public MappingServlet() {

    initParameter = new HashMap<>();
  }

  public String getDescription() {

    return description;
  }

  public String getServletClassName() {

    return servletClassName;
  }


  public int getLoadOnStartup() {

    return loadOnStartup;
  }


  public void setDescription(String description) {

    this.description = description;
  }


  public void setServletClassName(String servletClassName) {

    this.servletClassName = servletClassName;
  }


  public void setLoadOnStartup(int loadOnStartup) {

    this.loadOnStartup = loadOnStartup;
  }

  public Map<String, String> getInitParameter() {

    return initParameter;
  }

  public void setInitParameter(String name, String value) {

    initParameter.put(name, value);
  }
}
