package test;

import static org.junit.Assert.assertEquals;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;
import org.junit.Before;
import org.junit.Test;
import server.ContentType;


public class ResourceTest {

  @Before
  public void setUp() throws Exception {

    // MyWAS myWas = new MyWAS(8080);
    // myWas.start();
  }

  @Test
  public void findImageTest() {

    String url = "/test/test.html";
    System.out.println(urlToPath(url));

    assertEquals(Files.exists(Paths.get("webapps" + urlToPath(url))), true);
    // assertEquals(Files.exists(Paths.get("resources\\" + urlToPath(url))), true);
  }

  private String urlToPath(String url) {

    url.replace('/', '\\');
    return url;
  }

  @Test
  public void parsingExtensionTest() {

    String url = "/favicon.ico";
    String extension = "";
    StringTokenizer ext = new StringTokenizer(url, ".");
    while (ext.hasMoreTokens()) {
      extension = ext.nextToken();
    }

    assertEquals(extension, "ico");
  }

  @Test
  public void mimeMappingtest() {

    String url = "/favicon.ico";
    String extension = "";

    StringTokenizer ext = new StringTokenizer(url, ".");
    while (ext.hasMoreTokens()) {
      extension = ext.nextToken();
    }

    assertEquals(ContentType.getMimeForExtension(extension), "image/x-icon");
  }



}
