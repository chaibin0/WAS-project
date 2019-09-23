package server;

enum StateCode {
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

  public int getStateCode() {

    return stateCode;
  }

  public String getDescription() {

    return description;
  }
}