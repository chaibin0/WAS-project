package server.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class HttpParsedRequest {

  private String version;

  private String method;

  private String requestUrl;

  private String url;

  private Hashtable<String, String> header;

  private Map<String, String> content;

  public HttpParsedRequest(BufferedReader reader)
      throws IOException, NoSuchElementException, InterruptedException {

    content = new HashMap<>();
    header = new Hashtable<>();


    String message = getMessage(reader);
    String[] lineSplit = message.split("\r\n", -1);
    if (lineSplit[0].isEmpty()) {
      return;
    }

    startLine(lineSplit[0]);


    // check header
    int length = lineSplit.length;
    if (method.equals("POST")) {
      // remove body type
      length -= 1;
    }

    // store header
    for (int i = 1; i < length; i++) {
      if (lineSplit[i].isEmpty() || lineSplit[i].equals(" ")) {
        break;
      }
      String headers = lineSplit[i];
      StringTokenizer st = new StringTokenizer(headers, ": ");
      String name = st.nextToken();
      String value = st.nextToken();
      header.put(name, value);
    }


    switch (method) {
      case "GET":
        StringTokenizer st = new StringTokenizer(requestUrl, "?");
        url = st.nextToken();
        if (st.hasMoreElements()) {
          parseContent(st.nextToken());
        }
        break;
      case "POST":
        url = requestUrl;
        parseContent(lineSplit[length]);
        break;
      case "PUT":
        url = requestUrl;
        parseContent(lineSplit[length]);
        break;
      case "DELETE":
        url = requestUrl;
        parseContent(lineSplit[length]);
        break;
      case "TRACE":
        break;
      case "CONNECT":
        break;

      default:
    }


  }


  private synchronized String getMessage(BufferedReader reader) throws IOException {

    String line = "";
    StringBuilder sb = new StringBuilder();

    while (!(line = reader.readLine()).equals("")) {
      sb.append(line).append("\r\n");
    }
    System.out.println("====message====");
    System.out.println(sb.toString());
    return sb.toString();

  }

  private void startLine(String line) {

    StringTokenizer startLineSplit = new StringTokenizer(line, " ");
    while (startLineSplit.hasMoreTokens()) {
      this.method = startLineSplit.nextToken();
      this.requestUrl = startLineSplit.nextToken();
      this.version = startLineSplit.nextToken();
    }

  }

  private boolean parseContent(String data) {

    boolean isKey = true;
    StringBuilder key = new StringBuilder();
    StringBuilder value = new StringBuilder();
    System.out.println("parseContent: " + data);
    for (int i = 0; i < data.length(); i++) {
      if (data.charAt(i) == '&') {
        isKey = true;
        content.put(key.toString(), value.toString());
        key = new StringBuilder();
        value = new StringBuilder();
        continue;
      }
      if (data.charAt(i) == '=') {
        isKey = false;
        continue;
      }
      if (isKey) {
        key.append(data.charAt(i));
      } else {
        value.append(data.charAt(i));
      }
    }

    if (key.length() != 0 && value.length() != 0) {
      content.put(key.toString(), value.toString());
    }

    return true;
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
