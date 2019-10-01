package server.enums;

/**
 * ServletContext, HttpSession, servletRequest의 속성, 추가, 삭제, 수정했을 때 발생하는 리스너 타입 클래스이다. 
 * 각 요소에는 리스너를 호출하기 위한 메소드명을 저장했다.
 * 
 */
public enum AttributeListenerType {
  ADD("attributeAdded"),
  REPLACE("attributeReplaced"),
  REMOVE("attributeRemoved");

  private String methodName;

  private AttributeListenerType(String methodName) {

    this.methodName = methodName;
  }

  public String getMethodName() {

    return methodName;
  }
}
