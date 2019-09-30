package server;

import java.util.Enumeration;
import java.util.Hashtable;
import servlet.http.HttpSession;

public class HttpSessionImpl implements HttpSession {

  private static long increment = 1;

  private String jessionId;

  private Hashtable<String, Object> sessionData;

  /**
   * 세션을 새로 만드는 생성자 메소드.
   */
  public HttpSessionImpl() {

    jessionId = String.valueOf(increment++);
    sessionData = new Hashtable<>();
  }

  @Override
  public String getId() {

    return jessionId;
  }

  @Override
  public Object getAttribute(String name) {

    if (sessionData.get(name) == null) {
      return (String) "";
    }
    return sessionData.get(name);
  }

  @Override
  public Enumeration<String> getAttributeNames() {

    return sessionData.keys();
  }

  @Override
  public void setAttribute(String name, Object value) {

    sessionData.put(name, value);
  }

  @Override
  public void removeAttribute(String name) {

    sessionData.remove(name);
  }

  @Override
  public void invalidate() {

    sessionData = new Hashtable<>();
  }


}
