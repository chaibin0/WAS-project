package server;


public class Main {

  /**
   * Run the web application.
   * @throws InterruptedException 
   */
  public static final void main(String[] args) {

    MyWAS myWas = new MyWAS(8080);
    myWas.start();

  }

}
