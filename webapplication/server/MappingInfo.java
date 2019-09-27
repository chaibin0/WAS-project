package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import servlet.ServletConfig;

/**
 * Web.xml을 통해 받은 정보들을 MappingInfo 클래스를 통해 저장한다.
 */
public class MappingInfo {

  private Map<String, MappingServlet> servletNameToClass;

  private Map<String, String> servletPatternToName;

  private Map<String, MappingFilter> filterNameToClass;

  private Map<String, List<String>> filterPattern;

  /**
   * 객체를 생성하고 인스턴스 변수들을 초기화한다.
   */
  public MappingInfo() {

    servletNameToClass = new HashMap<>();
    servletPatternToName = new HashMap<>();
    filterNameToClass = new HashMap<>();
    filterPattern = new HashMap<>();
  }

  /**
   * 서블릿 이름을 통해서 ServletConfig에 필요한 데이터를 초기화하고 반환한다.
   * 
   * @param servletName 서블릿 이름
   * @return
   */
  public ServletConfig getServletConfig(String servletName) {

    return new MyServletConfig(servletName, getServletInitParamter(servletName));
  }

  /**
   * 서블릿에 저장된 초기화 파라미터를 가져온다.
   */
  private Map<String, String> getServletInitParamter(String servletName) {

    return servletNameToClass.get(servletName).getInitParameter();
  }

  /**
   * 서블릿 이름을 통해 서블릿 객체를 찾을 수 있도록 저장하는 메소드.
   * 
   * @param servletName 서블릿 이름
   * @param servlet 매핑으로 만든 서블릿 객체
   */
  public void setServletClass(String servletName, MappingServlet servlet) {

    servletNameToClass.put(servletName, servlet);
  }

  public void setUrlServletPattern(String urlPattern, String servletName) {

    servletPatternToName.put(urlPattern, servletName);
  }

  public boolean containsServletClassType(String servletName) {

    return servletNameToClass.containsKey(servletName);
  }

  public boolean containsServletPattern(String url) {

    return servletPatternToName.containsKey(url);
  }

  public String getServletName(String url) {

    return servletPatternToName.get(url);
  }

  public MappingServlet getServletClassType(String servletName) {

    return servletNameToClass.get(servletName);
  }

  public MappingFilter getFilterClass(String filterName) {

    return filterNameToClass.get(filterName);
  }

  public void setFilterClass(String filterName, MappingFilter filterClass) {

    filterNameToClass.put(filterName, filterClass);
  }


  public List<String> getFilterPattern(String urlPattern) {

    return filterPattern.getOrDefault(urlPattern, new ArrayList<>());
  }

  /**
   * url에 대응하는 필터 이름을 저장한다.
   * 
   * @param urlPattern url 정보
   * @param filterName 필터 이름
   */
  public void setFilterPattern(String urlPattern, String filterName) {

    List<String> filterNames = filterPattern.getOrDefault(urlPattern, new ArrayList<>());
    filterNames.add(filterName);
    filterPattern.put(urlPattern, filterNames);
  }

}


