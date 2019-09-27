package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import servlet.RequestDispatcher;
import servlet.ServletInputStream;
import servlet.http.Cookie;
import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;
import servlet.http.HttpSession;

/**
 * HttpServletRequest 인터페이스를 구현한 객체 클래스이다.
 */
public class Request implements HttpServletRequest {

  private BufferedReader reader;

  private Map<String, String> requestParameter;

  private String method;

  /**
   * Request 객체를 초기화하고 인스턴스 변수들을 초기화한다.
   * 
   * @param parsedRequest HttpRequest 메시지를 저장한 클래스
   * @throws IOException IOException
   */
  public Request(HttpParsedRequest parsedRequest) throws IOException {

    requestParameter = new HashMap<>();
    initRequestParameter(parsedRequest);
    method = parsedRequest.getMethod();
    // 미구현
    // servletInputStream = new ServletInputStreamImpl(clientSocket);
  }

  /**
   * Request메시지를 통해 requestParameter 데이터를 가져온다.(방어적 복사를 통해 가져옴)
   */
  private void initRequestParameter(HttpParsedRequest parsedRequest) {

    for (String key : parsedRequest.getContentKeys()) {
      requestParameter.put(key, parsedRequest.getContent(key));
    }
  }

  @Override
  public Cookie[] getCookies() {

    return null;
  }

  @Override
  public String getParameter(String name) {

    return requestParameter.get(name);
  }

  @Override
  public String[] getParameterValues(String name) {

    return (String[]) requestParameter.keySet().toArray();
  }

  @Override
  public Map<String, String[]> getParameterMap() {

    return null;
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {

    // InputStream inputStream = clientSocket.getInputStream();
    return null;
  }

  @Override
  public BufferedReader getReader() throws IOException {

    return reader;
  }
  
  @Override
  public String getMethod() {

    return method;
  }

  @Override
  public RequestDispatcher getRequestDispatcher(String path) {

    return new RequestDispatcher() {

      @Override
      public void forward(HttpServletRequest request, HttpServletResponse response) {

        Container container = Container.getInstance();
        container.dispatch(path, request, response);

      }
    };
  }

  @Override
  public HttpSession getSession() {

    return null;
  }

}
