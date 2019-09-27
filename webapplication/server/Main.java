package server;


public class Main {

  private static final int PORT = 8080;

  /**
   * Web Application Server를 포트를 지정하고 시작한다.
   */
  public static final void main(String[] args) {
    MyWas myWas = new MyWas(PORT);
    myWas.start();
  }

}
