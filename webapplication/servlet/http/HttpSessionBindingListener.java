package servlet.http;

import servlet.http.HttpSessionBindingEvent;

public interface HttpSessionBindingListener {

  default public void valueBound(HttpSessionBindingEvent event) {}

  default public void valueUnbound(HttpSessionBindingEvent event) {}
}
