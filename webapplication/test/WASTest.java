package test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
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

  @SuppressWarnings("null")
  @Test
  public void logTest() {

    final StackTraceElement[] trace = Thread.currentThread().getStackTrace();
    String method = trace[trace.length - 1].toString();
    System.out.println(method);
    PrintWriter pw = null;
    try {
      LocalDate today = LocalDate.now();
      DateTimeFormatter format = DateTimeFormatter.ofPattern("-yyyy-MM-d");
      Path path = Paths.get("logs\\socketLog" + format.format(today) + ".txt");
      if (!Files.exists(path)) {
        Files.createFile(path);
      }
      pw = new PrintWriter(new FileWriter(path.toString(), true));
      pw.println("ㅎㅎㅎ");
      throw new IOException("ggg");

    } catch (IOException e) {
      for (StackTraceElement s : e.getStackTrace()) {
        pw.println(s.toString());
        System.out.println(s.toString());
      }
      pw.flush();
    }
  }

  public void log(String logs) {


  }



}
