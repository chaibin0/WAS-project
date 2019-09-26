package test;

import java.io.IOException;
import org.junit.Ignore;
import server.MyWas;


public class WASTest {

  /**
   * 클라이언트에서 메시지 받는지 확인.
   * 
   * @throws IOException
   * @throws InterruptedException
   */
  @Ignore
  public void wasTest() throws IOException {

    // 쓰레드풀을 만들어서 접속할때마다 쓰레드를 생성한다.
    MyWas mywas = new MyWas(8080);
    mywas.start();
  }

}
