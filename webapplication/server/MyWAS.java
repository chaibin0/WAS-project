package server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
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
        clientSocket.setKeepAlive(true);
        Runnable request = () -> {
          try {
            container.requestHttp(clientSocket);
            clientSocket.close();
          } catch (IOException e) {
            e.printStackTrace();
          } catch (NoSuchElementException e) {
            e.printStackTrace();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        };
        Thread thread = new Thread(request);

        System.out.println(thread.getName());
        threadFool.execute(thread);



      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }


}
