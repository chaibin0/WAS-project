package servlet;

import servlet.ServletContextAttributeEvent;

public interface ServletContextAttributeListener {

  default public void attributeAdded(ServletContextAttributeEvent event) {}

  default public void attributeRemoved(ServletContextAttributeEvent event) {}

  default public void attributeReplaced(ServletContextAttributeEvent event) {}
}
