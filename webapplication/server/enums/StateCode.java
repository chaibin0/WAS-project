package server.enums;

/**
 * 응답코드를 나타내는 클래스 상태 코드를 통해서 클라이언트에게 기본적인 상태 정보를 전송한다. 
 * stateCode : 클래스에 관한 상태코드를 의미한다. 
 * description : 상태코드에 대한 기본적인 정보를 저장한 문자열이다.
 */

public enum StateCode {
  OK(200, "ok"),
  MOVED_PERMANENTLY(301, "moved permanently"),
  BAD_REQUEST(400, "Bad Request"),
  NOT_FOUND(404, "Not found"),
  SERVER_ERROR(500, "Intenal Server Error");

  int stateCode;

  String description;

  StateCode(int stateCode, String description) {

    this.stateCode = stateCode;
    this.description = description;
  }

  /**
   * 상태코드를 StateCode 객체로 변환한다.
   * @param sc 상태코드
   * @return StateCode object
   */
  public static StateCode fromString(int sc) {

    for (StateCode stateCode : StateCode.values()) {
      if (stateCode.getStateCode() == sc) {
        return stateCode;
      }
    }
    return NOT_FOUND;
  }

  public int getStateCode() {

    return stateCode;
  }

  public String getDescription() {

    return description;
  }
}
