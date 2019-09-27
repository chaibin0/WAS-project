package server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;

/**
 * HttpServletResponse 인터페이스를 구현한 클래스.
 */
public class Response implements HttpServletResponse {

  private static final String HTML11 = "HTTP/1.1";

  private static final String HEADER_DELIMETER = ": ";

  private static final String WHITE_SPACE = " ";

  private static final String LINE = "\r\n";

  private StateCode stateCode;

  private ContentType contentType;

  private Socket clientSocket;

  private Map<String, String> responseHeader = new HashMap<>();

  /**
   * Response 객체를 생성하고 초기화한다.
   * 
   * @param request HttpServletRequest 정보
   * @param clientSocket 클라이언트 소켓
   */
  public Response(Socket clientSocket, HttpServletRequest request) {

    stateCode = StateCode.OK;
    contentType = ContentType.HTML;
    this.clientSocket = clientSocket;
    setInitHeaderDefault();

  }

  /**
   * 기본 헤더 정보를 넣어준다.
   */
  private void setInitHeaderDefault() {

    responseHeader.put("Content-Type", contentType.getMime());
    // responseHeader.put("Set-Cookie", "JSESSIONID=1;");
  }


  @Override
  public PrintWriter getWriter() throws IOException {

    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
    sendGeneral(writer);
    sendHeader(writer);
    return writer;
  }

  @Override
  public OutputStream getOutputStream() throws IOException {

    OutputStream outputStream = clientSocket.getOutputStream();
    sendGeneral(outputStream);
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

    for (String header : responseHeader.keySet()) {
      outputStream
          .write((header + HEADER_DELIMETER + responseHeader.get(header) + LINE).getBytes());
    }
    outputStream.write(LINE.getBytes());
  }

  /**
   * PrintHeader를 통해서 헤더를 전송한다.
   * 
   * @param writer 문자열 출력스트림
   */
  private void sendHeader(PrintWriter writer) {

    for (String header : responseHeader.keySet()) {
      writer.println(header + HEADER_DELIMETER + responseHeader.get(header));
    }
    writer.println(LINE);
  }

  /**
   * PrintHeader를 통해서 General line을 전송한다.
   * 
   * @param writer 문자열 출력 스트림
   */
  private void sendGeneral(PrintWriter writer) {

    writer.println(
        HTML11 + WHITE_SPACE + stateCode.getStateCode() + WHITE_SPACE + stateCode.getDescription());
  }

  /**
   * OutputStream를 통해서 General line을 전송한다.
   * 
   * @param writer 바이트 출력 스트림
   */
  private void sendGeneral(OutputStream outputStream) throws IOException {

    String generalHeader = HTML11 + WHITE_SPACE + stateCode.getStateCode() + WHITE_SPACE
        + stateCode.getDescription() + LINE;
    byte[] buf = generalHeader.getBytes();
    outputStream.write(buf);
  }

  @Override
  public void sendRedirect(String location) throws IOException {

    responseHeader.put("Location", location);
    stateCode = StateCode.MOVED_PERMANENTLY;
    getWriter().close();

  }

  @Override
  public void setHeader(String name, String value) {

    responseHeader.put(name, value);
  }

}
