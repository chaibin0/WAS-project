package servlet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ServletInputStream extends InputStream {

  Socket socket;

  public int readLine() throws IOException {

    BufferedInputStream stream = new BufferedInputStream(socket.getInputStream());
    return stream.read();
  }

  @Override
  public int read() throws IOException {

    return socket.getInputStream().read();
  }

}
