package server;

import java.util.HashMap;
import java.util.Map;

class MappingServlet {

  int loadOnStartup;

  String description;

  String servletClassName;

  Map<String, String> initParameter;

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
}
