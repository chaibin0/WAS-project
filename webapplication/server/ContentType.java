package server;


public enum ContentType {
  HTML("html", "text/html; charset=UTF-8"),
  TEXT("txt", "text/plain; charset=UTF-8"),
  CSS("css", "text/css"),
  GIF("gif", "image/gif"),
  JPG("jpg", "image/jpg"),
  JPEG("jpg", "image/jpeg"),
  PNG("png", "image/png"),
  ICO("ico", "image/x-icon");

  String extension;

  String mime;

  ContentType(String extension, String mime) {

    this.extension = extension;
    this.mime = mime;
  }

  public String getExtension() {

    return extension;
  }

  public String getMime() {

    return mime;
  }

  /**
   * get MIME using extension. if extension is html, return "text/html".
   * 
   * @param extension extension
   * @return contentType to String
   */
  public static String getMimeForExtension(String extension) {

    for (ContentType type : ContentType.values()) {
      if (type.getExtension().equals(extension)) {
        return type.mime;
      }
    }
    return "text/plain";
  }
}
