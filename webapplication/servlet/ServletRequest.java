package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public interface ServletRequest {

  public Object getParameter(String name);

  public Object[] getParameterValues(String name);

  public Map<String, String[]> getParameterMap();

  public ServletInputStream getInputStream() throws IOException;

  public BufferedReader getReader() throws IOException;

  public void setAttribute(String name, Object o);
  
  public void removeAttribute(String name);
  

}
