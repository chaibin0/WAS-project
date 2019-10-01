package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import server.exception.FaultElementException;
import server.log.MyLogger;

/**
 * WebXml을 분석하는 클래스.
 *
 */
public class WebXml {

  private static final MyLogger logger = MyLogger.getLogger();

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
    } catch (FaultElementException e) {
      e.printStackTrace();
      logger.errorLog(e.getStackTrace());
    } catch (IOException e) {
      e.printStackTrace();
      logger.errorLog(e.getStackTrace());

    }

  }

  /**
   * xml을 읽고 XmlParser 객체를 통해서 xml 태그를 파싱한다.
   */
  private void readXml(Path path) {

    logger.log("xml parsing : " + path.toString());

    try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
      XmlParser parser = new XmlParser();
      String xmlData = parser.getFiles(reader);
      xml = parser.parse(xmlData, 0, xmlData.length());

    } catch (IOException e) {
      e.printStackTrace();
      logger.errorLog(e.getStackTrace());

    }
  }

  private boolean isWebXml(Path path) {

    return Files.exists(Paths.get(path.toAbsolutePath() + WEB_XML));
  }

  public Xml getXml() {

    return xml;
  }

  /**
   * xml을 각각의 태그에 따라 매핑한다. 처음에는 webapp태그를 통한 데이터를 매핑한다.
   * 
   * @throws FaultElementException webapp태그의 관련되지 않는 태그가 올경우 에러가 발생한다.
   * 
   */
  public void xmlMapping(Path directory, Xml xml) throws FaultElementException {

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
        case "filter":
          xmlMappingFilterNameToClass(directory, subTag);
          break;
        case "filter-mapping":
          xmlMappingFilterPattern(directory, subTag);
          break;
        case "listener":
          xmlMappingListener(directory, subTag);
          break;
        default:
      }
    }
  }

  private void xmlMappingListener(Path directory, Xml listener) {

    for (Xml subTag : listener.getSubTag()) {
      switch (subTag.getTagName()) {
        case "listener-class":
          mappingInfo.addListener(subTag.getValue());
          break;
        default:
      }
    }


  }

  /**
   * ContextParam 태그를 매핑하는 메소드.
   * 
   * @throws FaultElementException ContextParam태그의 관련되지 않는 태그가 올경우 에러가 발생한다.
   */
  private void xmlMappingContextParam(Path directory, Xml xmlContextInit)
      throws FaultElementException {

    String name = "";
    String value = "";

    for (Xml subTag : xmlContextInit.getSubTag()) {
      switch (subTag.getTagName()) {
        case "param-name":
          name = subTag.getValue();
          break;
        case "param-value":
          value = subTag.getValue();
          break;
        default:
          throw new FaultElementException("매핑 에러");
      }
    }

    if (name.isEmpty() || value.isEmpty()) {
      throw new FaultElementException("매핑 에러");
    }

    mappingInfo.setServletContextInitParam(name, value);
  }

  /**
   * filter-Mapping 태그를 매핑하는 메소드.
   * 
   * @throws FaultElementException filter-Mapping 관련되지 않는 태그가 올경우 에러가 발생한다.
   */
  private void xmlMappingFilterPattern(Path directory, Xml filterMapping)
      throws FaultElementException {

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
          throw new FaultElementException("매핑 에러");
      }
    }
    for (String pattern : patterns) {
      mappingInfo.setFilterPattern(pattern, filterName);
    }
  }

  /**
   * filter 태그를 매핑하는 메소드.
   * 
   * @throws FaultElementException filter태그의 관련되지 않는 태그가 올경우 에러가 발생한다.
   */
  private void xmlMappingFilterNameToClass(Path directory, Xml filter)
      throws FaultElementException {

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
          throw new FaultElementException("매핑 에러");
      }
    }
    if (!name.isEmpty() && !className.isEmpty()) {
      mappingInfo.setFilterClass(name, mappingFilter);
    }
  }

  /**
   * Servlet-Mapping 태그를 매핑하는 메소드.
   * 
   * @throws FaultElementException Servlet-Mapping 태그의 관련되지 않는 태그가 올경우 에러가 발생한다.
   */
  private void xmlMappingServletPatternToName(Path directory, Xml xmlServletMappingInfo)
      throws FaultElementException {

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
          throw new FaultElementException("매핑 에러");

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
   * servlet태그를 매핑하는 메소드.
   * 
   * @throws FaultElementException servlet태그의 관련되지 않는 태그가 올경우 에러가 발생한다.
   */
  private void xmlMappingServletNameToClass(Xml xmlServletInfo) throws FaultElementException {

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
          throw new FaultElementException("매핑 에러");
      }
    }

    if (mappingInfo.containsServletClassType(servletName)) {
      System.out.println("servletName : " + servletName + " 이미 존재하는 서블릿 이름입니다.");
      return;
    }
    mappingInfo.setServletClass(servletName, servlet);
  }

  /**
   * servlet 태그에 있는 init-param에 태그들을 매핑하고 MappingServlet에 저장하는 메소드.
   * 
   * @throws FaultElementException init-param태그의 관련되지 않는 태그가 올경우 에러가 발생한다.
   */
  private void xmlMappingServletInitParameter(MappingServlet servlet, Xml xmlServletInit)
      throws FaultElementException {

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
          throw new FaultElementException("매핑 에러");
      }
    }

    if (name.isEmpty() || value.isEmpty()) {
      throw new FaultElementException("매핑 에러");
    }

    servlet.setInitParameter(name, value);
  }
}
