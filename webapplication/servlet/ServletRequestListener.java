package servlet;

import servlet.ServletRequestEvent;

public interface ServletRequestListener {

  default public void requestDestroyed(ServletRequestEvent sre) {}

  default public void requestInitialized(ServletRequestEvent sre) {}
}
