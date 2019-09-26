package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Web Application Server 클래스.
 */
public class MyWas {

  private int port;

  private Container container;

  private static final int MAX_REQUEST = 100;

  /**
   * 포트를 설정한다.
   * 
   * @param port 포트번호
   */
  public MyWas(int port) {

    container = Container.getInstance();
    this.port = port;
  }

  /**
   * Web Application Server를 수행한다. web.xml을 먼저 처리하고 클라이언트에게 요청 받아 처리한다.
   */
  public void start() {

    // xml mapping
    WebXml webXml = new WebXml();
    webXml.parseAllWebXml();

    // ready for client request
    try (ServerSocket wasServer = new ServerSocket(port)) {
      // limit request
      ExecutorService threadFool = Executors.newFixedThreadPool(MAX_REQUEST);

      while (true) {
        Socket clientSocket = wasServer.accept();
        clientSocket.setKeepAlive(true);
        clientSocket.setSoTimeout(5000);

        Runnable request = () -> {
          try {
            container.requestHttp(clientSocket);
            clientSocket.close();
          } catch (IOException e) {
            System.out.println("파일 처리에 실패하였습니다.");
            e.printStackTrace();
          } catch (NoSuchElementException e) {
            System.out.println("서블릿이 존재하지 않습니다.");
            // 404
            e.printStackTrace();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        };
        Thread thread = new Thread(request);
        threadFool.execute(thread);

      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }


}
