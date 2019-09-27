package server;

import java.io.IOException;
import java.util.List;
import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;

public class MyFilter {

  private Container container;

  private MyFilterChain filterChain;


  private String url;

  MyFilter(String url) {

    this.url = url;
    container = Container.getInstance();
    makeFilterChain();
  }

  // 체인 생성
  private void makeFilterChain() {

    try {
      List<String> filterNames = container.getMappingInfo().getFilterPattern(url);
      filterChain = new MyFilterChain(url);
      MyFilterChain iterator = filterChain;
      for (String filterName : filterNames) {
        iterator.setFilter(Class
            .forName(container.getMappingInfo().getFilterClass(filterName).getFilterClassName()));
        iterator.setNext(new MyFilterChain(url));
        iterator = filterChain.getNext();
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

  }

  public void doFilter(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    filterChain.doFilter(request, response);
  }

}
