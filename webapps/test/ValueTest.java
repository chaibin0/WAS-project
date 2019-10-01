package test;

import servlet.http.HttpSessionBindingEvent;
import servlet.http.HttpSessionBindingListener;

public class ValueTest implements HttpSessionBindingListener {

  private String name;

  public ValueTest(String name) {

    this.name = name;
  }

  public String getName() {

    return name;
  }

  @Override
  public void valueBound(HttpSessionBindingEvent event) {

    System.out.println("bound 테스트");
  }



}
