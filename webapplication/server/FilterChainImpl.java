package server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import server.log.MyLogger;
import servlet.FilterChain;
import servlet.FilterConfig;
import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;

class FilterChainImpl implements FilterChain {

  private static final MyLogger logger = MyLogger.getLogger();

  private String url;

  private String filterName;

  private Class<?> filter;

  private FilterChainImpl next;

  private Container container;

  public FilterChainImpl(String url) {

    container = Container.getInstance();
    this.url = url;
  }

  /**
   * FilterChain이 연결되어 있는 filter객체에서 filter를 실행하는 메소드이다. 만약 다음 필터가 존재하지 않을 경우 서블릿을 실행한다.
   */
  public void doFilter(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    if (next == null) {
      container.executeServlet(url, request, response);
      return;
    }

    try {
      Class<?>[] paramType = {servlet.http.HttpServletRequest.class,
          servlet.http.HttpServletResponse.class, servlet.FilterChain.class};
      Method method = filter.getDeclaredMethod("doFilter", paramType);
      // Object instance =filter.newInstance();
      Object instance = getFilterInstance();
      Object[] param = {request, response, next};
      method.invoke(instance, param);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
      logger.errorLog(e.getStackTrace());
    } catch (SecurityException e) {
      e.printStackTrace();
      logger.errorLog(e.getStackTrace());
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      logger.errorLog(e.getStackTrace());
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      logger.errorLog(e.getStackTrace());
    } catch (InvocationTargetException e) {
      e.printStackTrace();
      logger.errorLog(e.getStackTrace());
    }

  }

  /**
   * 필터의 인스턴스를 가져오는 메소드. 만약 필터가 한번도 인스턴스화가 되지 않았으면 필터 객체를 초기화합니다.
   * 
   * @return 필터 객체를 반환
   */
  private Object getFilterInstance() {

    if (container.containsLoadedFilter(filter)) {
      return container.getLoadedFilter(filter);
    } else {
      return newFilterInstance();
    }

  }

  /**
   * 필터 객체를 생성하는 메소드. 필터가 처음으로 생성하면 init메소드를 수행합니다.
   * 
   * @return 필터 객체를 반환
   */
  private Object newFilterInstance() {

    Object instance = null;
    try {
      instance = filter.newInstance();
      invokeInit(instance);
    } catch (InstantiationException e) {
      e.printStackTrace();
      logger.errorLog(e.getStackTrace());
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      logger.errorLog(e.getStackTrace());
    }
    return instance;
  }

  /**
   * filter 객체의 init를 invoke하는 메소드. init메소드는 FilterConfig의 정보를 담고 init를 invoke합니다.
   * 
   * @param instance 필터 객체 인스턴스
   */
  private void invokeInit(Object instance) {

    logger.log("filter init : " + filterName);

    try {
      Class<?>[] typeParam = {servlet.FilterConfig.class};
      Method init = filter.getDeclaredMethod("init", typeParam);
      FilterConfig config = container.getMappingInfo().getFilterConfig(filterName);
      init.invoke(instance, config);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
      logger.errorLog(e.getStackTrace());
    } catch (SecurityException e) {
      e.printStackTrace();
      logger.errorLog(e.getStackTrace());
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      logger.errorLog(e.getStackTrace());
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      logger.errorLog(e.getStackTrace());
    } catch (InvocationTargetException e) {
      e.printStackTrace();
      logger.errorLog(e.getStackTrace());
    }

  }

  public void setFilterName(String filterName) {

    this.filterName = filterName;
  }

  public Class<?> getFilter() {

    return filter;
  }

  public FilterChainImpl getNext() {

    return next;
  }

  public void setFilter(Class<?> filter) {

    this.filter = filter;
  }

  public void setNext(FilterChainImpl next) {

    this.next = next;
  }
}
