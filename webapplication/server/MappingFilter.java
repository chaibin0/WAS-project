package server;

import java.util.HashMap;
import java.util.Map;

/**
 * Web.xml로부터 데이터를 분석된 필터 정보를 저장하는 클래스.
 *
 */
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
