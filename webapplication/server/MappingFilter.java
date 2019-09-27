package server;

import java.util.HashMap;
import java.util.Map;

public class MappingFilter {

  String filterClassName;

  Map<String, String> initParameter;

  public MappingFilter() {

    initParameter = new HashMap<>();
  }


  public String getFilterClassName() {

    return filterClassName;
  }


  public Map<String, String> getInitParameter() {

    return initParameter;
  }


  public void setFilterClassName(String filterClassName) {

    this.filterClassName = filterClassName;
  }

}
