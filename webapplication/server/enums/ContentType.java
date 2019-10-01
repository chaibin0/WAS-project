package server.enums;


/**
 * url 요청을 통해 받은 정적 데이터들을 확장자를 통해 ContentType을 변경하기 위한 클래스.
 *
 */
public enum ContentType {
  HTML("html", "text/html; charset=UTF-8"),
  TEXT("txt", "text/plain; charset=UTF-8"),
  CSS("css", "text/css"),
  JS("js", "text/javascript"),
  GIF("gif", "image/gif"),
  JPG("jpg", "image/jpg"),
  JPEG("jpg", "image/jpeg"),
  PNG("png", "image/png"),
  ICO("ico", "image/x-icon");

  String extension;

  String mime;

  /**
   * 확장자와 Content-Type을 가지는 생성자.
   * 
   * @param extension 확장자 타입
   * @param mime Content-type
   */
  ContentType(String extension, String mime) {

    this.extension = extension;
    this.mime = mime;
  }

  /**
   * 확장자를 통해서 ContentType을 구하는 메소드이다.
   * 
   * @param extension 확장자 {html,jpg}
   * @return ContentType ContentType enum을 반환
   */
  public static ContentType fromString(String extension) {

    for (ContentType type : ContentType.values()) {
      if (type.getExtension().equals(extension)) {
        return type;
      }
    }
    // default conttent type
    return ContentType.TEXT;

  }

  /**
   * 확장자를 통해 mime타입을 구하는 메소드.
   * 
   * @param extension extension 확장자 {html,jpg}
   * @return ContentType 문자열을 반환한다.
   */
  public static String getMimeForExtension(String extension) {

    for (ContentType type : ContentType.values()) {
      if (type.getExtension().equals(extension)) {
        return type.mime;
      }
    }
    return "";
  }

  public String getExtension() {

    return extension;
  }

  public String getMime() {

    return mime;
  }

}
