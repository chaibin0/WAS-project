package servlet.http;

import java.io.IOException;
import servlet.RequestDispatcher;
import servlet.ServletRequest;

public interface HttpServletRequest extends ServletRequest {

  public String getMethod() throws IOException;

  public RequestDispatcher getRequestDispatcher(String path);

  public Cookie[] getCookies();

  public HttpSession getSession(boolean create);

  public HttpSession getSession();
  
}
