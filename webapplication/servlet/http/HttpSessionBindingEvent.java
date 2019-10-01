package servlet.http;


public class HttpSessionBindingEvent {

  private HttpSession session;

  private String name;

  private Object value;

  public HttpSessionBindingEvent(HttpSession session, String name, Object value) {

    this.session = session;
    this.name = name;
    this.value = value;
  }

  public HttpSession getSession() {

    return session;
  }

  public String getName() {

    return name;
  }

  public Object getValue() {

    return value;
  }



}
