package servlet;

import servlet.ServletRequestAttributeEvent;

public interface ServletRequestAttributeListener {

  default public void attributeAdded(ServletRequestAttributeEvent srae) {}

  default public void attributeRemoved(ServletRequestAttributeEvent srae) {}

  default public void attributeReplaced(ServletRequestAttributeEvent srae) {}
}
