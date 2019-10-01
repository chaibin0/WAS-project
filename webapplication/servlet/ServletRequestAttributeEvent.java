package servlet;


public class ServletRequestAttributeEvent {

  private String name;

  private Object value;

  public ServletRequestAttributeEvent(String name, Object value) {

    this.name = name;
    this.value = value;
  }

  public String getName() {

    return name;
  }

  public Object getValue() {

    return value;
  }

}
