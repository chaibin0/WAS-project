package servlet;


public interface ServletContextListener {

  default void contextInitialized(ServletContextEvent sce) {}

  default void contextDestroyed(ServletContextEvent sce) {}
}
