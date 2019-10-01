package servlet.http;

import servlet.http.HttpSessionEvent;

public interface HttpSessionListener {

  default public void sessionCreated(HttpSessionEvent se) {}

  default public void sessionDestroyed(HttpSessionEvent se) {}
}
