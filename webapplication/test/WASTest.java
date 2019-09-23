package test;

import java.io.IOException;
import org.junit.Test;
import server.MyWAS;


public class WASTest {

  /**
   * 클라이언트에서 메시지 받는지 확인
   * 
   * @throws IOException
   */
  @Test
  public void requestTest() throws IOException {

    // 쓰레드풀을 만들어서 (10명정도) 접속할때마다 쓰레드를 생성한다.
    // log기록할것
    MyWAS mywas=new MyWAS(8080);
    mywas.start();
    
    
  }

}
