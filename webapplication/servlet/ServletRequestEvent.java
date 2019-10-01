package servlet;


public class ServletRequestEvent {

  private ServletRequest servletRequest;

  public ServletRequestEvent(ServletRequest servletRequest) {

    this.servletRequest = servletRequest;
  }

  public ServletRequest getServletRequest() {

    return servletRequest;
  }


}
