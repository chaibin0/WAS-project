package server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import server.http.HttpParsedRequest;
import servlet.ServletConfig;
import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;


public class Container {

  private static Container container = new Container();

  private MappingInfo mappingInfo;

  private Map<Class<?>, Object> loadedClass = new HashMap<>();

  private Container() {

    mappingInfo = new MappingInfo();
  }

  public static Container getInstance() {

    return container;
  }

  /**
   * 
   * @param clientSocket
   * @throws IOException
   */
  public void requestHttp(Socket clientSocket) throws IOException {

    // parsed http message
    HttpParsedRequest parsedRequest = new HttpParsedRequest(clientSocket);

    HttpServletRequest request = new Request(parsedRequest);
    HttpServletResponse response = new Response(clientSocket);

    // get URL
    String url = parsedRequest.getUrl();

    dispatch(url, request, response);

  }

  public void dispatch(String url, HttpServletRequest request, HttpServletResponse response) {

    Runnable runnable = () -> {
      if (mappingInfo.getServletPatternToName().containsKey(url)) {
        executeServlet(url, request, response);
      } else if (Files.exists(Paths.get("webapps" + urlToPath(url)))) { // 해당프로젝트 파일 존재
        sendResource(Paths.get("webapps" + urlToPath(url)), response);
      } else if (Files.exists(Paths.get("resources" + urlToPath(url)))) { // WAS 안에 리소스 파일 존재
        sendResource(Paths.get("resources" + urlToPath(url)), response);
      }
    };

    runnable.run();
  }


  private String urlToPath(String url) {

    System.out.println(url);
    url.replace('/', '\\');
    return url;
  }

  private void sendResource(Path path, HttpServletResponse response) {

    // URL 분석
    String extension = getExtension(path);
    System.out.println("extension : " + extension);
    // contentype 설정
    response.setHeader("Content-Type", ContentType.getMimeForExtension(extension));

    // 데이터 전송
    try (PrintWriter writer = response.getWriter();
        BufferedReader input =
            new BufferedReader(new InputStreamReader(new FileInputStream(path.toString())))) {

      String line = "";
      while ((line = input.readLine()) != null) {
        writer.print(line);
      }

      writer.flush();
      writer.close();

    } catch (FileNotFoundException e) {
      // 없으면 안보냄
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String getExtension(Path path) {

    String extension = "";
    StringTokenizer ext = new StringTokenizer(path.toString(), ".");
    while (ext.hasMoreTokens()) {
      extension = ext.nextToken();
    }
    return extension;
  }

  public void executeServlet(String url, HttpServletRequest request, HttpServletResponse response) {

    String servletName = mappingInfo.getServletPatternToName().get(url);
    MappingServlet mappingServlet = mappingInfo.getServletNameToClass().get(servletName);

    try {
      Class<?> servlet = Class.forName(mappingServlet.getServletClassName());
      Object instance = getServletClass(servlet, servletName);
      invokeServiceMethod(instance, servlet, request, response);

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }

  }

  private void invokeServiceMethod(Object instance, Class<?> servlet, HttpServletRequest request,
      HttpServletResponse response) {

    try {
      Object[] param = {request, response};
      Class<?>[] typeParam =
          {servlet.http.HttpServletRequest.class, servlet.http.HttpServletResponse.class};
      Method method = servlet.getSuperclass().getDeclaredMethod("service", typeParam);
      method.invoke(instance, param);

    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }

  }

  private Object getServletClass(Class<?> servlet, String servletName) {

    if (!loadedClass.containsKey(servlet)) {
      Object instance = createNewInstance(servlet, servletName);
      invokeInit(instance, servlet, servletName);
      return instance;
    }

    return loadedClass.get(servlet);
  }

  /**
   * 서블릿이 한번도 초기화되지 않았다면 init를 실행해주고 ServletConfig를 초기화 해준다.
   * 
   * @param instance servlet object
   * @param servlet servlet class type
   * @param servletName servlet name
   */
  private void invokeInit(Object instance, Class<?> servlet, String servletName) {

    try {

      Class<?>[] typeParam = {servlet.ServletConfig.class};
      Method init = servlet.getSuperclass().getSuperclass().getDeclaredMethod("init", typeParam);
      ServletConfig config = mappingInfo.getInitParameter(servletName);
      init.invoke(instance, config);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }

  }

  private Object createNewInstance(Class<?> servlet, String servletName) {

    Object instance = null;
    try {

      instance = servlet.newInstance();
      loadedClass.put(servlet, instance);
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
    return instance;

  }

  public void annotaionMapping() {

  }

  public MappingInfo getMappingInfo() {

    return mappingInfo;
  }



}
