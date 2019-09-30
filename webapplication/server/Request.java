package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
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
class Request implements HttpServletRequest {

  private Container container;

  private Socket clientSocket;

  private Map<String, String> requestParameter;

  private String method;

  private HttpParsedRequest parsedRequest;

  /**
   * Request 객체를 초기화하고 인스턴스 변수들을 초기화한다.
   * 
   * @param clientSocket
   * 
   * @param parsedRequest HttpRequest 메시지를 저장한 클래스
   * @throws IOException IOException
   */
  public Request(Socket clientSocket, HttpParsedRequest parsedRequest) throws IOException {

    this.clientSocket = clientSocket;
    this.parsedRequest = parsedRequest;
    container = Container.getInstance();
    requestParameter = new HashMap<>();
    initRequestParameter(parsedRequest);
    method = parsedRequest.getMethod();
  }

  /**
   * Request메시지를 통해 requestParameter 데이터를 가져온다. 깊은 복사를 통해 데이터를 가져 온다.
   */
  private void initRequestParameter(HttpParsedRequest parsedRequest) {

    for (String key : parsedRequest.getContentKeys()) {
      requestParameter.put(key, parsedRequest.getContent(key));
    }
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

    return new ServletInputStream(clientSocket.getInputStream());
  }

  @Override
  public BufferedReader getReader() throws IOException {

    return new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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

        container.dispatch(path, request, response);
      }
    };
  }

  @Override
  public Cookie[] getCookies() {

    Map<String, String> cookieMap = parsedRequest.getCookies();
    Cookie[] cookies = new Cookie[cookieMap.size()];
    int cookieCount = 0;
    for (String name : cookieMap.keySet()) {
      cookies[cookieCount] = new Cookie(name, cookieMap.get(name));
    }
    return cookies;
  }

  @Override
  public HttpSession getSession(boolean create) {

    String sessionId = parsedRequest.getSessionId();
    if (sessionId.isEmpty() || !create) {
      return null;
    } else if (sessionId.isEmpty() || create || !container.containsSession(sessionId)) {
      HttpSessionImpl session = new HttpSessionImpl();
      container.setSession(session.getId(), session);
      parsedRequest.setSessionId(session.getId());
      return session;
    } else {
      return container.getSession(sessionId);
    }
  }

  @Override
  public HttpSession getSession() {

    String sessionId = parsedRequest.getSessionId();
    if (sessionId.isEmpty() || !container.containsSession(sessionId)) {
      HttpSessionImpl session = new HttpSessionImpl();
      container.setSession(session.getId(), session);
      parsedRequest.setSessionId(session.getId());
      return session;
    } else {
      return container.getSession(sessionId);
    }
  }

}
