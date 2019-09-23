package servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public interface ServletResponse {

  PrintWriter getWriter() throws IOException;

  OutputStream getOutputStream() throws IOException;

}
