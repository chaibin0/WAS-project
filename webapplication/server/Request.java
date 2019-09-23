package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import server.http.HttpParsedRequest;
import servlet.RequestDispatcher;
import servlet.ServletInputStream;
import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;

public class Request implements HttpServletRequest {

  private ServletInputStream servletInputStream;

  private BufferedReader reader;

  private Map<String, String> parameter;

  private String method;

  public Request(HttpParsedRequest parsedRequest) throws IOException {

    reader = new BufferedReader(new InputStreamReader(parsedRequest.getSocket().getInputStream()));
    parameter = new HashMap<>();
    initRequestParameter(parsedRequest);
    method = parsedRequest.getMethod();
    // 미구현
    // servletInputStream = new ServletInputStreamImpl(clientSocket);
  }

  private void initRequestParameter(HttpParsedRequest parsedRequest) {

    for (String key : parsedRequest.getContent().keySet()) {
      parameter.put(key, parsedRequest.getContent().get(key));
    }
  }

  @Override
  public String getParameter(String name) {

    return null;
  }

  @Override
  public String[] getParameterValues(String name) {


    return null;
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
}
