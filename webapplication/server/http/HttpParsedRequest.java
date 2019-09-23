package server.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class HttpParsedRequest {

  private Socket socket;

  private String version;

  private String method;

  private String requestUrl;

  private String url;

  private Hashtable<String, String> header;

  private Map<String, String> content;

  public HttpParsedRequest(Socket clientSocket) throws IOException, NoSuchElementException {

    this.socket = clientSocket;


    BufferedReader reader =
        new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    // String message = getMessage(reader);

    content = new HashMap<>();
    header = new Hashtable<>();
    StringTokenizer st;
    String line = "";
    // 기본 정보 확인
    if ((line = reader.readLine()) != null) {
      System.out.println(line);

      st = new StringTokenizer(line);
      method = st.nextToken().toUpperCase();
      requestUrl = st.nextToken();
      version = st.nextToken();
    } else {
      throw new IOException();
    }

    // 헤더 확인
    while (!(line = reader.readLine()).equals("")) {
      System.out.println(line);
      st = new StringTokenizer(line, ": ");
      String name = st.nextToken();
      String value = st.nextToken();
      header.put(name, value);
    }

    switch (method) {
      case "GET":
        st = new StringTokenizer(requestUrl, "?");
        url = st.nextToken();
        System.out.println("요청URL :" + url);
        while (st.hasMoreTokens()) {
          parseContent(st.nextToken());
        }
        break;
      case "POST":
        System.out.print(reader.readLine());
        parseContent(reader.readLine());
        break;
      case "PUT":
        break;
      case "DELETE":
        break;
      case "TRACE":
        break;
      case "CONNECT":
        break;

      default:
    }


  }

  private String getMessage(BufferedReader reader) throws IOException {

    StringBuffer message = new StringBuffer();
    String line = "";

    while ((line = reader.readLine()) != null) {
      message.append(line);
    }

    return message.toString();
  }

  private boolean parseContent(String data) {

    StringTokenizer st = new StringTokenizer(data, "&");
    try {

      while (st.hasMoreTokens()) {
        StringTokenizer pair = new StringTokenizer(st.nextToken(), "=");
        String key = pair.nextToken();
        String value = pair.nextToken();
        content.put(key, value);
      }
    } catch (NoSuchElementException e) {
      content.clear();
      return false;
    }
    return true;

  }

  public Socket getSocket() {

    return socket;
  }


  public String getVersion() {

    return version;
  }


  public String getMethod() {

    return method;
  }


  public String getRequestUrl() {

    return requestUrl;
  }


  public String getUrl() {

    return url;
  }


  public Hashtable<String, String> getHeader() {

    return header;
  }


  public Map<String, String> getContent() {

    return content;
  }


  public void setSocket(Socket socket) {

    this.socket = socket;
  }


  public void setVersion(String version) {

    this.version = version;
  }


  public void setMethod(String method) {

    this.method = method;
  }


  public void setRequestUrl(String requestUrl) {

    this.requestUrl = requestUrl;
  }


  public void setUrl(String url) {

    this.url = url;
  }


  public void setHeader(Hashtable<String, String> header) {

    this.header = header;
  }


  public void setContent(Map<String, String> content) {

    this.content = content;
  }


}
