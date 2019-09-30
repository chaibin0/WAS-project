package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Xml 문서를 파싱한 데이터를 해당 클래스에 저장한다. 트리 형태로 구성되어 있다.
 */
public class Xml {

  private Xml parent;

  private String tagName;

  private String value;

  private List<Xml> subTag;

  private Map<String, String> attributesMap;

  /**
   * Xml를 생성하고 초기화한다.
   * 
   * @param parent 상위 태그
   * @param tagName 태그이름
   */
  public Xml(Xml parent, String tagName) {

    attributesMap = new HashMap<>();
    subTag = new ArrayList<>();
    this.parent = parent;
    this.tagName = tagName;
  }

  /**
   * 태그에 대한 속성들을 파싱하고 저장한다.
   * 
   * @param attributes 속성정보
   */
  public void parseAndSetAttributes(String attributes) {

    StringTokenizer st = new StringTokenizer(attributes, "\t");
    while (st.hasMoreTokens()) {
      String[] attribute = st.nextToken().split("=", 2);
      attributesMap.put(attribute[0], attribute[1]);
    }

  }

  public Xml getParent() {

    return parent;
  }


  public String getTagName() {

    return tagName;
  }


  public String getValue() {

    return value;
  }


  public List<Xml> getSubTag() {

    return subTag;
  }


  public void setParent(Xml parent) {

    this.parent = parent;
  }


  public void setTagName(String tagName) {

    this.tagName = tagName;
  }


  public void setValue(String value) {

    this.value = value;
  }


  public void setSubTag(List<Xml> subTag) {

    this.subTag = subTag;
  }


}
