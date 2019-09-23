package server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;

public class MyWAS {

  private int port;

  private Container container;

  public MyWAS(int port) {

    this.port = port;
  }

  public void start() {

    // jar file loader(시간되면 할것)

    // xml mapping

    WebXml webXml = new WebXml();
    webXml.parseAllWebXml();
    container = Container.getInstance();

    // ready for client request
    try (ServerSocket wasServer = new ServerSocket(port)) {

      // limit request
      ExecutorService threadFool = Executors.newFixedThreadPool(10);

      while (true) {
        Socket clientSocket = wasServer.accept();

        Runnable request = () -> {
          try {
            System.out.println("***응답***");
            container.requestHttp(clientSocket);
          } catch (IOException e) {
            e.printStackTrace();
          }
        };
        threadFool.execute(request);

      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }


}
