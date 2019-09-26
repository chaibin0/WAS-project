package servlet;

import java.util.Enumeration;

public interface ServletContext {

  public String getInitParameter(String name);

  public Enumeration<String> getInitParameterNames();

  public boolean setInitParameter(String name, String value);
}
