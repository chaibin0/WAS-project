package server.exception;

/**
 * web.xml에서 매핑이 실패할 경우 발생하는 예외 클래스이다.
 * 속성 태그에 나올 수 없는 태그들이 존재할 경우 예외가 발생한다.
 *
 */
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
