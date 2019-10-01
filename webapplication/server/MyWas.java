package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import server.log.MyLogger;

/**
 * Web Application Server 클래스.
 */
public class MyWas {

  private static final MyLogger logger = MyLogger.getLogger();

  private static final int MAX_REQUEST = 100;

  private int port;

  private Container container;



  /**
   * 포트를 설정한다.
   * 
   * @param port 포트번호
   */
  public MyWas(int port) {

    container = Container.getInstance();

    // xml mapping
    WebXml webXml = new WebXml();
    webXml.parseAllWebXml();

    container.initListener();
    container.initServletContext();
    this.port = port;
  }

  /**
   * Web Application Server를 수행한다. web.xml을 먼저 처리하고 클라이언트에게 요청 받아 처리한다.
   */
  public void start() {

    logger.log("Web Start");

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
          } catch (IOException e) {
            e.printStackTrace();
            logger.errorLog(e.getStackTrace());
          } catch (NoSuchElementException e) {
            e.printStackTrace();
            logger.errorLog(e.getStackTrace());
          } catch (InterruptedException e) {
            e.printStackTrace();
            logger.errorLog(e.getStackTrace());
          } finally {
            try {
              clientSocket.close();
            } catch (IOException e) {
              e.printStackTrace();
              logger.errorLog(e.getStackTrace());
            }
          }
        };
        Thread thread = new Thread(request);
        threadFool.execute(thread);

      }
    } catch (IOException e) {
      e.printStackTrace();
      logger.errorLog(e.getStackTrace());
    }

  }


}
