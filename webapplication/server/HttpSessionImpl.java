package server;

import java.util.Enumeration;
import java.util.Hashtable;
import server.enums.AttributeListenerType;
import server.enums.HttpSessionBindingListenerType;
import server.enums.HttpSessionLifeCycleListenerType;
import servlet.http.HttpSession;
import servlet.http.HttpSessionBindingListener;

public class HttpSessionImpl implements HttpSession {

  private static long increment = 1;

  private String jessionId;

  private Hashtable<String, Object> sessionData;

  private transient ListenerContainer listenerContainer;

  /**
   * 세션을 새로 만드는 생성자 메소드.
   */
  public HttpSessionImpl() {

    listenerContainer = ListenerContainer.getListenerContainer();
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

    if (value instanceof HttpSessionBindingListener) {
      listenerContainer.eventHttpSessionBingListener(HttpSessionBindingListenerType.BOUND, this,
          name, value);
    }

    if (sessionData.contains(name)) {
      listenerContainer.eventHttpSessionAttribute(AttributeListenerType.REPLACE, this, name, value);
    } else {
      listenerContainer.eventHttpSessionAttribute(AttributeListenerType.ADD, this, name, value);
    }
    sessionData.put(name, value);
  }

  @Override
  public void removeAttribute(String name) {

    if (!sessionData.containsKey(name)) {
      return;
    }

    Object value = sessionData.get(name);
    if (value instanceof HttpSessionBindingListener) {
      listenerContainer.eventHttpSessionBingListener(HttpSessionBindingListenerType.UNBOUND, this,
          name, value);
    }
    listenerContainer.eventHttpSessionAttribute(AttributeListenerType.REPLACE, this, name, value);
    sessionData.remove(name);
  }

  @Override
  public void invalidate() {

    listenerContainer.eventHttpSession(HttpSessionLifeCycleListenerType.DESTROY, this);
    sessionData = new Hashtable<>();
  }


}
