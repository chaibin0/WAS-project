package test;

import servlet.ServletRequestAttributeEvent;
import servlet.ServletRequestAttributeListener;

public class RequestAttributeListener implements ServletRequestAttributeListener {

  @Override
  public void attributeAdded(ServletRequestAttributeEvent srae) {

    System.out.println("request 데이터 추가 됨");
  }

  @Override
  public void attributeRemoved(ServletRequestAttributeEvent srae) {

    System.out.println("request 데이터 삭제 됨");

  }

  @Override
  public void attributeReplaced(ServletRequestAttributeEvent srae) {

    System.out.println("request 데이터 교체 됨");

  }

}
