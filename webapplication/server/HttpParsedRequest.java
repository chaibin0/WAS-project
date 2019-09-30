package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;
import server.log.MyLogger;

/**
 * 요청받은 HTTP request 헤더를 분석해서 저장하는 클래스이다. HTTP의 헤더와 body데이터를 저장한다.
 */
class HttpParsedRequest {

  private static final MyLogger logger = MyLogger.getLogger();

  private static final String HEADER_DELIMETER = ": ";

  private static final String LINE = "\r\n";

  private static final String GET_PARAMETER_DELIMETER = "?";

  private static final char PARAMETER_DELIMETER = '=';

  private static final char PARAMETER_TOKEN = '&';

  private String version;

  private String method;

  private String requestUrl;

  private String url;

  private String sessionId = "";

  private Hashtable<String, String> header;

  private Map<String, String> cookies;

  private Map<String, String> content;


  /**
   * HttpParsedRequest 생성자를 통해서 데이터를 파싱하고 저장한다.
   * 
   * @param reader 클라이언트로부터 받는 입력 스트림
   * @throws IOException IOException
   * @throws NoSuchElementException NoSuchElementException
   * @throws InterruptedException InterruptedException
   */
  public HttpParsedRequest(BufferedReader reader)
      throws IOException, NoSuchElementException, InterruptedException {

    content = new HashMap<>();
    header = new Hashtable<>();

    String message = getMessage(reader);

    // included empty string
    String[] lineSplit = message.split(LINE, -1);
    logger.log("request : " + lineSplit[0]);
    // illegal general header
    if (lineSplit[0].isEmpty()) {
      return;
    }
    initGeneralHeader(lineSplit[0]);
    initHeader(lineSplit);
    initContent(reader);
    initCookies();
    initSessionId();
  }

  private void initHeader(String[] lineSplit) {

    // check header
    int length = lineSplit.length;

    // store header
    for (int i = 1; i < length; i++) {
      if (lineSplit[i].isEmpty() || lineSplit[i].equals(" ")) {
        break;
      }
      String[] headers = lineSplit[i].split(HEADER_DELIMETER, 2);
      String name = headers[0];
      String value = headers[1];
      if (!name.isEmpty() || !value.isEmpty()) {
        header.put(name, value);
      }
    }
  }

  private void initContent(BufferedReader reader) throws IOException {

    switch (method) {
      case "GET":
        StringTokenizer st = new StringTokenizer(requestUrl, GET_PARAMETER_DELIMETER);
        url = st.nextToken();
        if (st.hasMoreElements()) {
          parseContent(st.nextToken());
        }
        break;
      case "POST":
        url = requestUrl;
        parseContent(bodyMessage(reader));
        break;
      case "PUT":
        url = requestUrl;
        parseContent(bodyMessage(reader));
        break;
      case "DELETE":
        url = requestUrl;
        break;
      case "TRACE":
        break;
      case "CONNECT":
        break;
      default:
    }
  }

  private void initSessionId() {

    if (cookies.containsKey("JSESSIONID")) {
      sessionId = cookies.get("JSESSIONID");
    } else {
      sessionId = "";
    }
  }

  /**
   * HTTP Request 데이터를 한번에 저장한다.
   */
  private synchronized String getMessage(BufferedReader reader) throws IOException {

    String line = "";
    StringBuilder sb = new StringBuilder();

    while (reader.ready() && !(line = reader.readLine()).equals("")) {
      sb.append(line).append(LINE);
    }
    reader = null;
    return sb.toString();
  }

  /**
   * 바디가 존재하는 메시지일 경우 HTTP/1.1의 명세에 따라 Content-Length의 길이만큼 데이터를 받아온다. POST, PUT등은 bodyMessage메소드를
   * 이용해서 데이터를 추가로 가져옵니다.
   */
  private synchronized String bodyMessage(BufferedReader reader) throws IOException {

    int length = Integer.parseInt(header.get("Content-Length"));
    StringBuilder sb = new StringBuilder();
    int data;
    for (int i = 0; i < length; i++) {
      data = reader.read();
      if (data == -1) {
        break;
      }
      sb.append((char) data);
    }

    return sb.toString();
  }

  /**
   * General line을 받는 메소드이다. method url version 순으로 받는다.
   */
  private void initGeneralHeader(String line) {

    StringTokenizer lineSplit = new StringTokenizer(line, " ");
    while (lineSplit.hasMoreTokens()) {
      this.method = lineSplit.nextToken();
      this.requestUrl = lineSplit.nextToken();
      this.version = lineSplit.nextToken();
    }

  }

  /**
   * requestParameter를 "="를 기준으로 key와 value로 나누고 "&"를 기준으로 parameter를 나눈다.
   */
  private boolean parseContent(String data) {

    boolean isKey = true;
    StringBuilder key = new StringBuilder();
    StringBuilder value = new StringBuilder();
    for (int i = 0; i < data.length(); i++) {
      if (data.charAt(i) == PARAMETER_TOKEN) {
        isKey = true;
        content.put(key.toString(), value.toString());
        key = new StringBuilder();
        value = new StringBuilder();
        continue;
      }
      if (data.charAt(i) == PARAMETER_DELIMETER) {
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


  public String getHeader(String name) {

    return header.get(name);
  }

  public String getContent(String key) {

    return content.get(key);
  }

  public Set<String> getContentKeys() {

    return content.keySet();
  }

  public boolean getHeaderKeys(String key) {

    return header.containsKey(key);
  }

  public String getSessionId() {

    return sessionId;
  }

  public void setSessionId(String sessionId) {

    this.sessionId = sessionId;
  }

  public Map<String, String> getCookies() {

    return cookies;
  }

  /**
   * client로부터 받아온 request Message의 쿠키를 Collection에 맞게 초기화한다.
   */
  private void initCookies() {

    if (getHeaderKeys("Cookie")) {
      cookies = parsedCookie(getHeader("Cookie"));

    } else {
      cookies = Collections.emptyMap();
    }
  }

  /**
   * 여러개의 쿠키를 파싱해서 Map에 저장할 수 있게 변환한다. 세미클론을 기준으로 쿠키 데이터를 분할한다.
   * 
   * @param rawCookie client로부터 받아온 cookie 헤더
   * @return Map Collection
   */
  private Map<String, String> parsedCookie(String rawCookie) {

    cookies = new HashMap<>();
    String name = "";
    String value = "";
    StringTokenizer st = new StringTokenizer(rawCookie, "; ");
    while (st.hasMoreTokens()) {
      String token = st.nextToken();
      if (token.contains("=")) {
        int eq = token.indexOf("=");
        name = token.substring(0, eq);
        value = token.substring(eq + 1, token.length());
        cookies.put(name, value);
      }
    }
    return cookies;
  }

}
