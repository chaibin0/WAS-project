package servlet.http;

import java.util.Enumeration;

public interface HttpSession {

  public String getId();

  public Object getAttribute(String name);

  public Enumeration<String> getAttributeNames();

  public void setAttribute(String name, Object value);

  public void removeAttribute(String name);

  public void invalidate();
}
