package server;


/**
 * 헤더를 저장하기 위한 클래스.
 */
public class Header {

  private String name;

  private String value;
  
  /**
   * 헤더의 이름과 값을 담고 있는 생성자 메소드.
   * 
   * @param name key
   * @param value value
   */
  public Header(String name, String value) {

    this.name = name;
    this.value = value;
  }


  public String getName() {

    return name;
  }


  public String getValue() {

    return value;
  }

}
