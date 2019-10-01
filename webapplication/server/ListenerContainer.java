package server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import server.enums.AttributeListenerType;
import server.enums.ContextLifeCycleListenerType;
import server.enums.HttpSessionBindingListenerType;
import server.enums.HttpSessionLifeCycleListenerType;
import server.enums.RequestLifeCycleListenerType;
import server.log.MyLogger;
import servlet.ServletContext;
import servlet.ServletContextAttributeEvent;
import servlet.ServletContextEvent;
import servlet.ServletRequest;
import servlet.ServletRequestAttributeEvent;
import servlet.ServletRequestEvent;
import servlet.http.HttpSession;
import servlet.http.HttpSessionBindingEvent;
import servlet.http.HttpSessionEvent;

/**
 * 모든 리스너에 대한 정보를 저장하고 이벤트가 발생하는 메소드를 가지고 있는 클래스이다.
 *
 */
class ListenerContainer {

  private static ListenerContainer listenerContainer;

  private static MyLogger logger = MyLogger.getLogger();

  private List<Class<?>> servletContextListenerList;

  private List<Class<?>> servletContextAttributeListenerList;

  private List<Class<?>> servletRequestAttributeListenerList;

  private List<Class<?>> servletRequestListenerList;

  private List<Class<?>> httpSessionListenerList;

  private List<Class<?>> httpSessionAttributeListenerList;

  /*
   * private 생성자
   */
  private ListenerContainer() {

    servletContextListenerList = new ArrayList<>();
    servletContextAttributeListenerList = new ArrayList<>();
    servletRequestAttributeListenerList = new ArrayList<>();
    servletRequestListenerList = new ArrayList<>();
    httpSessionListenerList = new ArrayList<>();
    httpSessionAttributeListenerList = new ArrayList<>();
  }

  public static ListenerContainer getListenerContainer() {

    if (listenerContainer == null) {
      listenerContainer = new ListenerContainer();
    }

    return listenerContainer;
  }

  /**
   * ServletContextListener 이벤트가 발생할 때 수행하는 메소드.
   * 
   * @param listenerType listener type
   * @param context ServletContext object
   */
  public void eventServletContext(ContextLifeCycleListenerType listenerType,
      ServletContext context) {

    if (servletContextListenerList.isEmpty()) {
      return;
    }

    try {
      ServletContextEvent event = new ServletContextEvent(context);

      for (Class<?> listener : servletContextListenerList) {
        Object instance = listener.newInstance();
        Class<?> paramType = ServletContextEvent.class;

        Method method = listener.getMethod(listenerType.getMethodName(), paramType);
        method.invoke(instance, event);
      }

    } catch (InstantiationException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (SecurityException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    }
  }

  /**
   * ServletContextAttributeListener 이벤트가 발생할 때 수행하는 메소드.
   * 
   * @param listenerType listener type
   * @param context ServletContext object\
   * @param name name
   * @param value value
   */
  public void eventContextAttribute(AttributeListenerType listenerType, ServletContext context,
      String name, String value) {

    if (servletContextAttributeListenerList.isEmpty()) {
      return;
    }

    try {
      ServletContextAttributeEvent event = new ServletContextAttributeEvent(context, name, value);

      for (Class<?> listener : servletContextAttributeListenerList) {
        Object instance = listener.newInstance();
        Class<?> paramType = ServletContextAttributeEvent.class;

        Method method = listener.getMethod(listenerType.getMethodName(), paramType);
        method.invoke(instance, event);
      }
    } catch (InstantiationException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (SecurityException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    }
  }

  /**
   * ServletRequestListener 이벤트가 발생할 때 수행하는 메소드.
   * 
   * @param listenerType listener type
   * @param context ServletRequest object
   */
  public void eventServletRequest(RequestLifeCycleListenerType listenerType,
      ServletRequest request) {

    if (servletRequestListenerList.isEmpty()) {
      return;
    }

    try {
      ServletRequestEvent event = new ServletRequestEvent(request);

      for (Class<?> listener : servletRequestListenerList) {
        Object instance = listener.newInstance();
        Class<?> paramType = ServletRequestEvent.class;

        Method method = listener.getMethod(listenerType.getMethodName(), paramType);
        method.invoke(instance, event);
      }

    } catch (InstantiationException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (SecurityException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    }
  }

  /**
   * ServletRequestAttributeListener 이벤트가 발생할 때 수행하는 메소드.
   * 
   * @param listenerType listener type
   * @param name name
   * @param value value
   */
  public void eventRequestAttribute(AttributeListenerType listenerType, String name, Object value) {

    if (servletRequestAttributeListenerList.isEmpty()) {
      return;
    }

    try {
      ServletRequestAttributeEvent event = new ServletRequestAttributeEvent(name, value);

      for (Class<?> listener : servletRequestAttributeListenerList) {
        Object instance = listener.newInstance();
        Class<?> paramType = ServletRequestAttributeEvent.class;

        Method method = listener.getMethod(listenerType.getMethodName(), paramType);
        method.invoke(instance, event);
      }
    } catch (InstantiationException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (SecurityException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    }
  }

  /**
   * HttpSessionListener 이벤트가 발생할 때 수행하는 메소드.
   * 
   * @param listenerType listener type
   * @param session HttpSession object
   */
  public void eventHttpSession(HttpSessionLifeCycleListenerType listenerType, HttpSession session) {

    if (httpSessionListenerList.isEmpty()) {
      return;
    }

    try {
      HttpSessionEvent event = new HttpSessionEvent(session);

      for (Class<?> listener : httpSessionListenerList) {
        Object instance = listener.newInstance();
        Class<?> paramType = HttpSessionEvent.class;

        Method method = listener.getMethod(listenerType.getMethodName(), paramType);
        method.invoke(instance, event);
      }

    } catch (InstantiationException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (SecurityException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    }
  }

  /**
   * HttpSessionAttributeListener 이벤트가 발생할 때 수행하는 메소드.
   * 
   * @param listenerType listener type
   * @param session HttpSession object
   * @param name name
   * @param value value
   */
  public void eventHttpSessionAttribute(AttributeListenerType listenerType, HttpSession session,
      String name, Object value) {

    if (httpSessionAttributeListenerList.isEmpty()) {
      return;
    }

    try {
      HttpSessionBindingEvent event = new HttpSessionBindingEvent(session, name, value);

      for (Class<?> listener : httpSessionAttributeListenerList) {
        Object instance = listener.newInstance();
        Class<?> paramType = HttpSessionBindingEvent.class;

        Method method = listener.getMethod(listenerType.getMethodName(), paramType);
        method.invoke(instance, event);
      }
    } catch (InstantiationException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (SecurityException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    }
  }

  /**
   * HttpSessionBindingListener 이벤트가 발생할 때 수행되는 메소드.
   * 
   * @param listenerType listener type
   * @param session HttpSession object
   * @param name name
   * @param value value
   */
  public void eventHttpSessionBingListener(HttpSessionBindingListenerType listenerType,
      HttpSession session, String name, Object value) {

    try {
      HttpSessionBindingEvent event = new HttpSessionBindingEvent(session, name, value);
      Class<?> paramType = HttpSessionBindingEvent.class;
      Class<?> classType = value.getClass();
      Method method;
      method = classType.getMethod(listenerType.getMethodName(), paramType);
      method.invoke(value, event);
    } catch (NoSuchMethodException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (SecurityException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      logger.errorLog(e.getStackTrace());
      e.printStackTrace();
    }

  }

  /**
   * 리스너 클래스에 구현되어 있는 인터페이스들을 검색해 해당 리스너 리스트에 저장한다.
   * 
   * @param listenerClass 리스너 클래스
   * @param interfaces 리스너 클래스에 구현되어 있는 인터페이스
   */
  public void addListener(Class<?> listenerClass, Class<?>[] interfaces) {

    for (Class<?> listener : interfaces) {
      switch (listener.getName()) {
        case "servlet.ServletContextListener":
          addServletContextListener(listenerClass);
          break;
        case "servlet.ServletContextAttributeListener":
          addServletContextAttributeListener(listenerClass);
          break;
        case "servlet.ServletRequestListener":
          addServletRequestListener(listenerClass);
          break;
        case "servlet.ServletRequestAttributeListener":
          addServletRequestAttributeListener(listenerClass);
          break;
        case "servlet.http.HttpSessionListener":
          addHttpSessionListener(listenerClass);
          break;
        case "servlet.http.HttpSessionAttributeListener":
          addHttpSessionAttributeListener(listenerClass);
          break;
        default:
      }
    }
  }

  private void addServletContextListener(Class<?> listenerClass) {

    servletContextListenerList.add(listenerClass);
  }

  private void addServletContextAttributeListener(Class<?> listenerClass) {

    servletContextAttributeListenerList.add(listenerClass);
  }

  private void addServletRequestListener(Class<?> listenerClass) {

    servletRequestListenerList.add(listenerClass);
  }

  private void addServletRequestAttributeListener(Class<?> listenerClass) {

    servletRequestAttributeListenerList.add(listenerClass);
  }

  private void addHttpSessionListener(Class<?> listenerClass) {

    httpSessionListenerList.add(listenerClass);
  }

  private void addHttpSessionAttributeListener(Class<?> listenerClass) {

    httpSessionAttributeListenerList.add(listenerClass);
  }
}
