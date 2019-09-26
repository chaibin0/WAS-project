package servlet.http;

import java.io.IOException;
import servlet.RequestDispatcher;
import servlet.ServletRequest;

public interface HttpServletRequest extends ServletRequest {

  public String getMethod() throws IOException;

  public HttpSession getSession();

  public RequestDispatcher getRequestDispatcher(String path);

  public Cookie[] getCookies();

}
