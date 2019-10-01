package server.enums;

/**
 * ServletRequest를 인스턴스화 하거나 소멸시킬 때 발생하는 리스너의 타입이다.
 * 각 요소에는 리스너를 호출하기 위한 메소드명을 저장했다.
 *
 */
public enum RequestLifeCycleListenerType {
  INIT("requestInitialized"),
  DESTROY("requestDestroyed");

  private String methodName;

  private RequestLifeCycleListenerType(String methodName) {

    this.methodName = methodName;
  }

  public String getMethodName() {

    return methodName;
  }
}
