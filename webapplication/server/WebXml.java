package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import servlet.ServletContext;

/**
 * WebXml을 분석하는 클래스.
 *
 */
public class WebXml {

  private static final String WEB_XML = "\\WEB-INF\\web.xml";

  private Xml xml;

  private Container container;

  private MappingInfo mappingInfo;

  /**
   * 객체를 생성하고 초기화한다.
   */
  public WebXml() {

    container = Container.getInstance();
    mappingInfo = container.getMappingInfo();
  }

  /**
   * webapps에 있는 모든 프로젝트 web.xml 파싱한다.
   */
  public void parseAllWebXml() {

    Path path = Paths.get("webapps");
    findWebXml(path);
  }

  /**
   * 모든 WebXml 데이터를 읽은 다음 xmlMapping메소드를 통해서 매핑한다.
   */
  private void findWebXml(Path path) {

    try {
      for (Path directory : Files.newDirectoryStream(path)) {
        if (isWebXml(directory)) {
          readXml(Paths.get(directory.toAbsolutePath() + WEB_XML));
          xmlMapping(directory.getFileName(), xml);
        }
      }
    } catch (IOException e) {
      System.out.println("디렉토리를 찾을 수 없습니다.");
      e.printStackTrace();
    } catch (Exception e) {
      System.out.println("잘못된 매핑");
      e.printStackTrace();
    }

  }

  /**
   * xml을 읽고 XmlParser 객체를 통해서 xml 태그를 파싱한다.
   */
  private void readXml(Path path) {

    try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
      XmlParser parser = new XmlParser();
      String xmlData = parser.getFiles(reader);
      xml = parser.parse(xmlData, 0, xmlData.length());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * xml이 존재하는지 확인하는 메소드.
   */
  private boolean isWebXml(Path path) throws IOException {

    return Files.exists(Paths.get(path.toAbsolutePath() + WEB_XML));
  }

  public Xml getXml() {

    return xml;
  }

  /**
   * xml을 각각의 태그에 따라 매핑한다. 처음에는 webapp태그를 통한 데이터를 매핑한다.
   */
  public void xmlMapping(Path directory, Xml xml) throws Exception {

    for (Xml subTag : xml.getSubTag()) {
      switch (subTag.getTagName()) {
        case "servlet":
          xmlMappingServletNameToClass(subTag);
          break;
        case "servlet-mapping":
          xmlMappingServletPatternToName(directory, subTag);
          break;
        case "context-param":
          xmlMappingContextParam(directory, subTag);
          break;
        // filter부터 이어서 할것
        case "filter":
          xmlMappingFilterNameToClass(directory, subTag);
          break;
        case "filter-mapping":
          xmlMappingFilterPattern(directory, subTag);
          break;
        default:
      }
    }
  }

  /**
   * ContextParam태그에 있는 내용들을 매핑한다.
   */
  private void xmlMappingContextParam(Path directory, Xml xmlContextInit) throws Exception {

    String name = "";
    String value = "";
    ServletContext context = container.getServletContext();
    for (Xml subTag : xmlContextInit.getSubTag()) {
      switch (subTag.getTagName()) {
        case "param-name":
          name = subTag.getValue();
          break;
        case "param-value":
          value = subTag.getValue();
          break;

        default:
          // faultXmlInfoError
      }
    }

    if (name.isEmpty() || value.isEmpty()) {
      throw new Exception("매핑 에러");
    }

    context.setInitParameter(name, value);
  }

  private void xmlMappingFilterPattern(Path directory, Xml filterMapping) {

    List<String> patterns = new ArrayList<>();

    String filterName = "";
    for (Xml subTag : filterMapping.getSubTag()) {
      switch (subTag.getTagName()) {
        case "url-pattern":
          patterns.add("/" + directory.getFileName() + subTag.getValue());
          break;
        case "filter-name":
          filterName = subTag.getValue();
          break;

        default:
          // faultXmlInfoError
      }
    }
    for (String pattern : patterns) {
      mappingInfo.setFilterPattern(pattern, filterName);
    }
  }

  private void xmlMappingFilterNameToClass(Path directory, Xml filter) {

    MappingFilter mappingFilter = new MappingFilter();
    String name = "";
    String className = "";
    for (Xml subTag : filter.getSubTag()) {
      switch (subTag.getTagName()) {
        case "filter-name":
          name = subTag.getValue();
          break;
        case "filter-class":
          className = subTag.getValue();
          mappingFilter.setFilterClassName(subTag.getValue());
          break;
        default:
          // faultXmlInfoError
      }
    }
    if (!name.isEmpty() && !className.isEmpty()) {
      mappingInfo.setFilterClass(name, mappingFilter);
    }
  }

  /**
   * ServletMapping에 있는 태그들을 매핑한다.
   */
  private void xmlMappingServletPatternToName(Path directory, Xml xmlServletMappingInfo) {

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
      if (mappingInfo.containsServletPattern(pattern)) {
        System.out.println("중복된 key");
        return;
      }
      mappingInfo.setUrlServletPattern(pattern, servletName);
    }
  }

  /**
   * Servlet태그에 있는 내용들을 매핑한다.
   */
  private void xmlMappingServletNameToClass(Xml xmlServletInfo) throws Exception {

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
          xmlMappingServletInitParameter(servlet, subTag);
          break;
        default:
          // faultXmlInfoError
      }
    }

    if (mappingInfo.containsServletClassType(servletName)) {
      System.out.println("servletName : " + servletName + " 이미 존재하는 서블릿 이름입니다.");
      return;
    }
    mappingInfo.setServletClass(servletName, servlet);
  }

  /**
   * servlet 태그에 있는 init-param에 태그들을 매핑하고 MappingServlet에 저장한다.
   */
  private void xmlMappingServletInitParameter(MappingServlet servlet, Xml xmlServletInit)
      throws Exception {

    String name = "";
    String value = "";

    for (Xml subTag : xmlServletInit.getSubTag()) {
      switch (subTag.getTagName()) {
        case "param-name":
          name = subTag.getValue();
          break;
        case "param-value":
          value = subTag.getValue();
          break;

        default:
          // faultXmlInfoError
      }
    }

    if (name.isEmpty() || value.isEmpty()) {
      throw new Exception("매핑 에러");
    }

    servlet.setInitParameter(name, value);
  }
}
