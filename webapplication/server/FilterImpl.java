package server;

import java.io.IOException;
import java.util.List;
import server.log.MyLogger;
import servlet.Filter;
import servlet.FilterChain;
import servlet.FilterConfig;
import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;

public class FilterImpl implements Filter {

  private static final MyLogger logger = MyLogger.getLogger();

  private Container container;

  private MappingInfo mappingInfo;

  private String url;

  private FilterConfig config;


  @Override
  public void init(FilterConfig filterConfig) {

    filterConfig = config;
  }

  FilterImpl(String url) {

    this.url = url;
    container = Container.getInstance();
    mappingInfo = container.getMappingInfo();
    makeFilterChain();
  }

  /**
   * 해당 url에 적용되는 모든 filter를 filterChain으로 연결하는 메소드.
   * 
   * @return FilterChain 객체를 반환
   */
  public FilterChain makeFilterChain() {

    FilterChainImpl filterChain = new FilterChainImpl(url);
    try {
      List<String> filterNames = container.getMappingInfo().getFilterPattern(url);
      FilterChainImpl iterator = filterChain;
      for (String filterName : filterNames) {
        iterator
            .setFilter(Class.forName(mappingInfo.getFilterClass(filterName).getFilterClassName()));
        iterator.setFilterName(filterName);
        iterator.setNext(new FilterChainImpl(url));

        iterator = filterChain.getNext();
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      logger.errorLog(e.getStackTrace());
    }
    return filterChain;

  }

  @Override
  public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException {

    chain.doFilter(request, response);
  }

}
