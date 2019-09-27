package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pc
 *
 */
public class FilterPattern {

  private String pattern;

  private List<String> filterNames;

  private Map<String, FilterPattern> subPattern;

  public FilterPattern(String pattern) {

    this.pattern = pattern;
    this.filterNames = new ArrayList<>();
    this.subPattern = new HashMap<>();

  }

  public String getPattern() {

    return pattern;
  }


  public List<String> getFilterNames() {

    return filterNames;
  }


  public Map<String, FilterPattern> getSubPattern() {

    return subPattern;
  }


  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj)
      return true;
    if (!(obj instanceof FilterPattern))
      return false;
    FilterPattern other = (FilterPattern) obj;
    if (pattern == null) {
      if (other.pattern != null)
        return false;
    } else if (!pattern.equals(other.pattern))
      return false;
    return true;
  }



}
