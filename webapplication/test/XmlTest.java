package test;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Ignore;
import org.junit.Test;
import server.Container;
import server.MappingInfo;
import server.WebXml;


public class XmlTest {

  /**
   * webapps파일에 여러 프로젝트가 있는지 확인.
   * 
   * @throws IOException
   */
  // @Test
  @Ignore
  public void checkProjectTest() throws IOException {

    Path path = Paths.get("webapps");
    System.out.println("root : " + path.toAbsolutePath().toString());
    System.out.println(Files.exists(path));

    for (Path directory : Files.newDirectoryStream(path)) {
      assertEquals(Files.exists(directory), true);
    }

  }

  /**
   * 프로젝트에 Web.xml파일이 있는지 확인.
   * 
   * @throws IOException
   */
  // @Test
  @Ignore
  public void findWebXmlFileTest() throws IOException {

    Path path = Paths.get("webapps");
    for (Path directory : Files.newDirectoryStream(path)) {
      Path xmlPath = Paths.get(directory.toAbsolutePath() + "\\WEB-INF\\web.xml");

      System.out.println(xmlPath.toAbsolutePath());
      assertEquals(Files.exists(xmlPath), true);
    }
  }

  /**
   * 파싱 테스트 root만 이상하게 저장되고 나머지는 정상작동.
   */
  // @Test
  @Ignore
  public void parserXmlTest() {

    WebXml webXML = new WebXml();
    webXML.parseAllWebXml();

  }

  /**
   * xml을 통한 파싱한 데이터를 mappingInfo에 매핑 테스트.
   */
  @Ignore
  public void mappingXmlTest() {

    Container container = Container.getInstance();
    WebXml webXml = new WebXml();
    webXml.parseAllWebXml();

    container = Container.getInstance();
//    container.xmlMapping(webXml.getXml());
    MappingInfo info = container.getMappingInfo();
    //접근 안됨
    /*
    for (String data : info.getServletPatternToName().keySet()) {
      System.out.println(data);
    }
    */
  }
}
