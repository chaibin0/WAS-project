package servlet.http;

public interface HttpSessionAttributeListener {

  default public void attributeAdded(HttpSessionBindingEvent event) {}

  default public void attributeRemoved(HttpSessionBindingEvent event) {}

  default public void attributeReplaced(HttpSessionBindingEvent event) {}

}
