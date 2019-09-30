package server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import servlet.http.HttpServletResponse;

/**
 * HttpServletResponse 인터페이스를 구현한 클래스.
 */
class Response implements HttpServletResponse {

  private static final String HTML11 = "HTTP/1.1";

  private static final String HEADER_DELIMETER = ": ";

  private static final String WHITE_SPACE = " ";

  private static final String LINE = "\r\n";

  private static final String EQ = "=";

  private static final String SEMICOLON_DELIMETER = "; ";

  private StateCode stateCode;

  private Socket clientSocket;

  private List<Header> responseHeader = new ArrayList<>();

  private HttpParsedRequest parsedRequest;


  /**
   * Response 객체를 생성하고 초기화한다.
   * 
   * @param clientSocket requested client Socket
   * @param parsedRequest requested message
   */
  public Response(Socket clientSocket, HttpParsedRequest parsedRequest) {

    stateCode = StateCode.OK;
    this.clientSocket = clientSocket;
    this.parsedRequest = parsedRequest;
  }

  private void initSessionIdToCookie(HttpParsedRequest parsedRequest) {

    // 이미 JSESSION 받은 데이터였으면 보내지 않는다.
    if (parsedRequest.getHeaderKeys("JSESSIONID")) {
      return;
    }
    if (!parsedRequest.getSessionId().isEmpty()) {
      StringBuilder session = new StringBuilder();
      session.append("JSESSIONID").append(EQ).append(parsedRequest.getSessionId())
          .append(SEMICOLON_DELIMETER);
      responseHeader.add(new Header("Set-Cookie", session.toString()));
    }
  }

  @Override
  public void addCookie(String name, String value) {

    StringBuilder cookie = new StringBuilder();
    cookie.append(name).append(EQ).append(value).append(SEMICOLON_DELIMETER);
    responseHeader.add(new Header("Set-Cookie", cookie.toString()));

  }

  @Override
  public PrintWriter getWriter() throws IOException {

    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
    sendHeader(writer);
    return writer;
  }

  @Override
  public OutputStream getOutputStream() throws IOException {

    OutputStream outputStream = clientSocket.getOutputStream();
    sendHeader(outputStream);
    outputStream.flush();
    return outputStream;
  }

  /**
   * outputStream을 통해서 헤더를 전송한다.
   * 
   * @param outputStream 바이트 출력스트림
   * @throws IOException IOException
   */
  private void sendHeader(OutputStream outputStream) throws IOException {

    String generalHeader = HTML11 + WHITE_SPACE + stateCode.getStateCode() + WHITE_SPACE
        + stateCode.getDescription() + LINE;
    byte[] buf = generalHeader.getBytes();
    outputStream.write(buf);

    for (Header header : responseHeader) {
      outputStream
          .write((header.getName() + HEADER_DELIMETER + header.getValue() + LINE).getBytes());
    }
    outputStream.write(LINE.getBytes());
  }

  /**
   * PrintHeader를 통해서 헤더를 전송한다.
   * 
   * @param writer 문자열 출력스트림
   */
  private void sendHeader(PrintWriter writer) {

    writer.println(
        HTML11 + WHITE_SPACE + stateCode.getStateCode() + WHITE_SPACE + stateCode.getDescription());
    initSessionIdToCookie(parsedRequest);
    for (Header header : responseHeader) {
      writer.println(header.getName() + HEADER_DELIMETER + header.getValue());
    }
    writer.println(LINE);
  }

  @Override
  public void sendRedirect(String location) throws IOException {

    responseHeader.add(new Header("Location", location));
    stateCode = StateCode.MOVED_PERMANENTLY;
    getWriter().close();

  }

  @Override
  public void setHeader(String name, String value) {

    responseHeader.add(new Header(name, value));
  }

  @Override
  public void setStatus(int sc) {

    stateCode = StateCode.fromString(sc);
  }

}
