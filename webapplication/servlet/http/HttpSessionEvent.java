package servlet.http;


public class HttpSessionEvent {

  HttpSession session;

  public HttpSessionEvent(HttpSession session) {

    this.session = session;
  }

  public HttpSession getSession() {

    return session;
  }

}
