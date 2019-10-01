package servlet;


public class ServletContextAttributeEvent {

  ServletContext sc;

  String name;

  String value;

  public ServletContextAttributeEvent(ServletContext sc, String name, String value) {

    this.sc = sc;
    this.name = name;
    this.value = value;
  }

  public ServletContext getServletContext() {

    return sc;
  }

  public String getName() {

    return name;
  }

  public String getValue() {

    return value;
  }


}
