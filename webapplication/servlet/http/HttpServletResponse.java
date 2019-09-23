package servlet.http;

import java.io.IOException;
import servlet.ServletResponse;

public interface HttpServletResponse extends ServletResponse {

  public void sendRedirect(String location) throws IOException;

  public void setHeader(String name, String value);

}
