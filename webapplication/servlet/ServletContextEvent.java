package servlet;


public class ServletContextEvent {

  private ServletContext sce;

  public ServletContextEvent(ServletContext sce) {

    this.sce = sce;
  }

  public ServletContext getServletContext() {

    return sce;
  }
}
