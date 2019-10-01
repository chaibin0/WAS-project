package server.log;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MyLogger {

  public static MyLogger logger;

  private PrintWriter pw;


  private MyLogger() {

    try {
      LocalDate today = LocalDate.now();
      DateTimeFormatter format = DateTimeFormatter.ofPattern("-yyyy-MM-d");
      Path path = Paths.get("logs\\myLog" + format.format(today) + ".txt");
      if (!Files.exists(path)) {
        Files.createFile(path);
      }
      pw = new PrintWriter(new FileWriter(path.toString(), true));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 로그 객체를 반환하는 메소드.
   * 
   * @return log 객체
   */
  public static MyLogger getLogger() {


    if (logger == null) {
      logger = new MyLogger();
    }
    return logger;
  }

  /**
   * log 메소드. [날짜][쓰레드이름][클래스이름 메소드이름]순으로 기록이 된다.
   * 
   * @param logs 로그 데이터
   */
  public void log(String logs) {

    final StackTraceElement[] trace = Thread.currentThread().getStackTrace();

    String threadName = Thread.currentThread().getName();
    String className = trace[trace.length - 1].getClassName();
    String methodName = trace[trace.length - 1].getMethodName();
    pw.print("[" + LocalDate.now() + "][" + threadName + "][" + className + " " + methodName + "]");
    pw.println(logs);
    pw.flush();
  }

  /**
   * 에러 메시지를 로그에 저장한다.
   * [날짜]->
   * stackTraceElement의 정보
   * @param e 예외 에러내용
   */
  public void errorLog(StackTraceElement[] e) {

    pw.println("[" + LocalDate.now() + "] ->");
    for (StackTraceElement element : e) {
      pw.println(element.toString());
    }
    pw.flush();
  }
}
