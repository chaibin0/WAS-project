package server.exception;

public class FaultElementException extends Exception {

  private static final long serialVersionUID = 1L;


  public FaultElementException() {

    super();
  }


  public FaultElementException(String message) {

    super(message);
  }


  @Override
  public String toString() {

    return "FaultElementException [getMessage()=" + getMessage() + "]";
  }


}
