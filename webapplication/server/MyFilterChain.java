package server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import servlet.FilterChain;
import servlet.http.HttpServletRequest;
import servlet.http.HttpServletResponse;

public class MyFilterChain implements FilterChain {

  private String url;

  private Class<?> filter;

  private MyFilterChain next;

  private Container container;

  public MyFilterChain(String url) {

    container = Container.getInstance();
    this.url = url;
  }

  public Class<?> getFilter() {

    return filter;
  }

  public MyFilterChain getNext() {

    return next;
  }

  public void setFilter(Class<?> filter) {

    this.filter = filter;
  }

  public void setNext(MyFilterChain next) {

    this.next = next;
  }

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
      Object instance = filter.newInstance();
      Object[] param = {request, response, next};
      method.invoke(instance, param);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }

  }
}
