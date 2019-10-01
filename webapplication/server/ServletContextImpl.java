package server;

import java.util.Enumeration;
import java.util.Hashtable;
import server.enums.AttributeListenerType;
import server.enums.ContextLifeCycleListenerType;
import servlet.ServletContext;


public class ServletContextImpl implements ServletContext {

  private Hashtable<String, String> initParameter;

  private ListenerContainer listener = ListenerContainer.getListenerContainer();

  /**
   * ServletContext 생성자. ServletContextListener가 등록되어 있을 경우 리스너를 수행한다.
   */
  public ServletContextImpl() {

    listener.eventServletContext(ContextLifeCycleListenerType.INIT, this);
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
      listener.eventContextAttribute(AttributeListenerType.REPLACE, this, name, value);
      initParameter.put(name, value);

      return false;
    }
    listener.eventContextAttribute(AttributeListenerType.ADD, this, name, value);
    initParameter.put(name, value);
    return true;
  }

}
