package servlet;

import java.io.IOException;
import java.io.InputStream;

public class ServletInputStream extends InputStream {

  InputStream in;

  public ServletInputStream(InputStream inputStream) {

    this.in = inputStream;
  }

  @Override
  public int read() throws IOException {

    return in.read();
  }

}
