package server;

import java.util.Collections;
import java.util.Map;
import servlet.FilterConfig;

/**
 * 필터에 관한 기본 정보를 담고 있는 클래스이다.
 */
public class FilterConfigImpl implements FilterConfig {

  private String filterName;

  private Map<String, String> initParameter;

  /**
   * filter를 매핑한 클래스에서 가져온 기본 정보를 저장한다.
   * 
   * @param filterName 필터이름
   * @param initParameter 초기화 파라미터(initParameter)
   */
  public FilterConfigImpl(String filterName, Map<String, String> initParameter) {

    this.filterName = filterName;
    this.initParameter = Collections.unmodifiableMap(initParameter);
  }


  public String getFilterName() {

    return filterName;
  }


  public String getInitParameter(String name) {

    return initParameter.get(name);
  }


  public void setFilterName(String filterName) {

    this.filterName = filterName;
  }



}
