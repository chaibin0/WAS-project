package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public interface ServletRequest {

  public String getParameter(String name);

  public String[] getParameterValues(String name);

  public Map<String, String[]> getParameterMap();

  public ServletInputStream getInputStream() throws IOException;

  public BufferedReader getReader() throws IOException;


}
