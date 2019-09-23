package server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import servlet.http.HttpServletResponse;

public class Response implements HttpServletResponse {

  private static final String HTML11 = "HTTP/1.1";

  StateCode stateCode;

  ContentType contentType;

  Socket clientSocket;

  Map<String, String> responseHeader = new HashMap<>();

  String httpVersion;

  public Response(Socket clientSocket) {

    stateCode = StateCode.OK;
    contentType = ContentType.HTML;
    this.clientSocket = clientSocket;
    setInitDefault();
  }

  private void setInitDefault() {

    responseHeader.put("Content-Type", ContentType.HTML.getMime());

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

    System.out.println("데이터 읽음");
    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
    sendGeneral(writer);
    sendHeader(writer);
    writer.flush();

    OutputStream outputStream = clientSocket.getOutputStream();
    return outputStream;
  }


  private void sendGeneral(PrintWriter writer) {

    writer.println(HTML11 + " " + stateCode.getStateCode() + " " + stateCode.getDescription());
  }

  private void sendHeader(PrintWriter writer) {

    for (String header : responseHeader.keySet()) {
      writer.println(header + ": " + responseHeader.get(header));
    }
    writer.println("\r\n");
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
