package server;

import java.util.Enumeration;
import java.util.Hashtable;
import servlet.ServletContext;


public class ServletContextImpl implements ServletContext {

  private Hashtable<String, String> initParameter;

  public ServletContextImpl() {

    initParameter = new Hashtable<>();
  }

  @Override
  public String getInitParameter(String name) {

    return initParameter.get(name);
  }

  @Override
  public Enumeration<String> getInitParameterNames() {

    return initParameter.keys();
  }

  @Override
  public boolean setInitParameter(String name, String value) {
    if (initParameter.contains(name)) {
      return false;
    }
    initParameter.put(name, value);
    return true;
  }

}
