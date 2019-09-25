package server;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import servlet.ServletConfig;

public class MappingInfo {

  private Map<String, MappingServlet> servletNameToClass;

  private Map<String, String> servletPatternToName;

  /**
   * initialize reference variable.
   */
  public MappingInfo() {

    servletNameToClass = new HashMap<>();
    servletPatternToName = new HashMap<>();
  }

  public void mapping(Path directory, Xml xml) throws Exception {

    for (Xml subTag : xml.getSubTag()) {
      switch (subTag.getTagName()) {
        case "servlet":
          mappingServletNameToClass(subTag);
          break;
        case "servlet-mapping":
          mappingServletPatternToName(directory, subTag);
          break;
        // filter부터 이어서 할것
        default:
      }
    }
  }

  private void mappingServletToFilter() {

  }

  private void mappingFilterNameToClass() {

  }

  /**
   * sub Tag of servlet. If subTag have servlet name, pattern will map url.
   * 
   * @param directory  path of directory
   * @param xmlServletMappingInfo servlet-name and url-pattern infomation
   */
  private void mappingServletPatternToName(Path directory, Xml xmlServletMappingInfo) {

    List<String> patterns = new ArrayList<>();
    String servletName = "";

    for (Xml subTag : xmlServletMappingInfo.getSubTag()) {
      switch (subTag.getTagName()) {
        case "servlet-name":
          servletName = subTag.getValue();
          break;
        case "url-pattern":
          patterns.add("/" + directory.getFileName() + subTag.getValue());
          break;
        default:
      }
    }

    if (servletName.isEmpty() || patterns.isEmpty()) {
      System.out.println("Servlet-Mapping error");
    }

    for (String pattern : patterns) {
      if (servletPatternToName.containsKey(pattern)) {
        System.out.println("중복된 key");
        return;
      }
      servletPatternToName.put(pattern, servletName);
    }
  }

  private void mappingServletNameToClass(Xml xmlServletInfo) throws Exception {

    MappingServlet servlet = new MappingServlet();
    String servletName = "";
    for (Xml subTag : xmlServletInfo.getSubTag()) {
      switch (subTag.getTagName()) {
        case "description":
          servlet.setDescription(subTag.getValue());
          break;
        case "servlet-name":
          servletName = subTag.getValue();
          break;
        case "load-on-startup":
          servlet.setLoadOnStartup(Integer.parseInt(subTag.getValue()));
          break;
        case "servlet-class":
          servlet.setServletClassName(subTag.getValue());
          break;
        case "init-param":
          mappingServletInitParameter(servlet, subTag);
          break;
        default:
          // faultXmlInfoError
      }
    }

    if (servletNameToClass.containsKey(servletName)) {
      System.out.println("servletName : " + servletName + " 이미 존재하는 서블릿 이름입니다.");
      return;
    }

    servletNameToClass.put(servletName, servlet);
  }

  private void mappingServletInitParameter(MappingServlet servlet, Xml xmlServletInit)
      throws Exception {

    String key = "";
    String value = "";

    for (Xml subTag : xmlServletInit.getSubTag()) {
      switch (subTag.getTagName()) {
        case "param-name":
          key = subTag.getValue();
          break;
        case "param-value":
          value = subTag.getValue();
          break;

        default:
          // faultXmlInfoError
      }
    }

    if (key.isEmpty() || value.isEmpty())
      throw new Exception("매핑 에러");

    servlet.getInitParameter().put(key, value);
  }

  public ServletConfig getInitParameter(String servletName) {

    Map<String, String> initParameter = servletNameToClass.get(servletName).getInitParameter();
    ServletConfig config = new MyServletConfig(servletName, initParameter);
    return config;
  }

  public Map<String, MappingServlet> getServletNameToClass() {

    return servletNameToClass;
  }


  public Map<String, String> getServletPatternToName() {

    return servletPatternToName;
  }


}


