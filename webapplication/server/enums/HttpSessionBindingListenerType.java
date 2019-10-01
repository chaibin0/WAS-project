package server.enums;


/**
 * HttpSession 객체가 session 데이터를 바인딩을 하면 수행되는 리스터 타입 클래스.
 * 각 요소에는 리스너를 호출하기 위한 메소드명을 저장했다.
 *
 */
public enum HttpSessionBindingListenerType {
  BOUND("valueBound"),
  UNBOUND("valueUnbound");

  private String methodName;

  private HttpSessionBindingListenerType(String methodName) {

    this.methodName = methodName;
  }

  public String getMethodName() {

    return methodName;
  }
}
