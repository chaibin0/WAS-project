package server;

import java.util.HashMap;
import java.util.Map;
import servlet.ServletConfig;

/**
 * Web.xml을 통해 받은 정보들을 MappingInfo 클래스를 통해 저장한다.
 */
public class MappingInfo {

  private Map<String, MappingServlet> servletNameToClass;

  private Map<String, String> servletPatternToName;

  /**
   * 객체를 생성하고 인스턴스 변수들을 초기화한다.
   */
  public MappingInfo() {

    servletNameToClass = new HashMap<>();
    servletPatternToName = new HashMap<>();
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

  public void setServletClass(String servletName, MappingServlet servlet) {

    servletNameToClass.put(servletName, servlet);
  }

  public void setUrlPattern(String urlPattern, String servletName) {

    servletPatternToName.put(urlPattern, servletName);
  }

  public boolean containsServletClassType(String servletName) {

    return servletNameToClass.containsKey(servletName);
  }

  public boolean containsPattern(String url) {

    return servletPatternToName.containsKey(url);
  }

  public String getServletName(String url) {

    return servletPatternToName.get(url);
  }

  public MappingServlet getServletClassType(String servletName) {

    return servletNameToClass.get(servletName);
  }
}


