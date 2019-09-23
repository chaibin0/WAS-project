package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class WebXml {

  private static final String WEB_XML = "\\WEB-INF\\web.xml";

  private Xml xml;

  private Container container = Container.getInstance();

  public void parseAllWebXml() {

    Path path = Paths.get("webapps");
    findWebXml(path);
  }

  private void findWebXml(Path path) {

    try {
      for (Path directory : Files.newDirectoryStream(path)) {
        if (isWebXml(directory)) {
          readXml(Paths.get(directory.toAbsolutePath() + WEB_XML));
          container.getMappingInfo().mapping(directory.getFileName(), xml);
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

  private void readXml(Path path) {

    try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));) {
      XmlParser parser = new XmlParser();
      String xmlData = parser.getFiles(reader);
      xml = parser.parse(xmlData, 0, xmlData.length());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private boolean isWebXml(Path path) throws IOException {

    return Files.exists(Paths.get(path.toAbsolutePath() + WEB_XML));
  }

  public Xml getXml() {

    return xml;
  }

}
