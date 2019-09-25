package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
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

  public void requestHttp(Socket clientSocket)
      throws IOException, NoSuchElementException, InterruptedException {

    // parsed http message
    try {
      BufferedReader reader =
          new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      HttpParsedRequest parsedRequest = new HttpParsedRequest(reader);
      HttpServletRequest request = new Request(parsedRequest);
      HttpServletResponse response = new Response(clientSocket);

      // get URL
      String url = parsedRequest.getUrl();
      if (url != null) {
        dispatch(url, request, response);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }



  public void dispatch(String url, HttpServletRequest request, HttpServletResponse response) {

    Runnable runnable = () -> {
      try {
        if (mappingInfo.getServletPatternToName().containsKey(url)) {
          executeServlet(url, request, response);
        } else if (Files.exists(Paths.get("webapps" + urlToPath(url)))) { // 해당프로젝트 파일 존재
          sendResource(Paths.get("webapps" + urlToPath(url)), response);
        } else if (Files.exists(Paths.get("resources" + urlToPath(url)))) { // WAS 안에 리소스 파일 존재
          sendResource(Paths.get("resources" + urlToPath(url)), response);
        }
      } catch (FileNotFoundException e) {
        System.out.println("file이 없습니다");
      } catch (IOException e) {
        e.printStackTrace();
      }
    };


    Thread thread = new Thread(runnable);
    thread.run();
    System.out.println("dispatch Thread : " + thread.getName());

  }


  private String urlToPath(String url) {

    url.replace('/', '\\');
    return url;
  }

  private void sendResource(Path path, HttpServletResponse response) throws IOException {

    // URL 분석
    String extension = getExtension(path);
    ContentType contentType = ContentType.fromString(extension);
    // contentype 설정
    response.setHeader("Content-Type", contentType.getMime());
    switch (contentType) {
      case JPG:
        sendImageFile(path, response, contentType);
      default:
        sendTextFile(path, response, contentType);
    }


  }

  private synchronized void sendImageFile(Path path, HttpServletResponse response,
      ContentType contentType) throws IOException {

    response.setHeader("Content-length", String.valueOf(path.toFile().length()));

    try {
      InputStream inputstream = new FileInputStream(path.toFile());
      OutputStream outputStream = response.getOutputStream();
      int content;

      while ((content = inputstream.read()) != -1) {
        outputStream.write(content);
      }
    } finally {
    }

  }

  private synchronized void sendTextFile(Path path, HttpServletResponse response,
      ContentType contentType) throws IOException {

    // 데이터 전송
    try {
      PrintWriter writer = response.getWriter();
      BufferedReader input =
          new BufferedReader(new InputStreamReader(new FileInputStream(path.toString())));
      String line = "";

      while ((line = input.readLine()) != null) {
        writer.print(line);
      }
      writer.flush();
    } finally {
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

    System.out.println("invokeServiceMethod()");
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

    System.out.println("invokeInit()");
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

    System.out.println("createNewInstance()");
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
