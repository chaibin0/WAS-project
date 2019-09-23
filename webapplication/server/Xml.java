package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Xml {

  private Xml parent;

  private String tagName;

  private String value;

  private List<Xml> subTag;

  private Map<String, String> attributes;

  public Xml(Xml parent, String tagName) {

    attributes = new HashMap<>();
    subTag = new ArrayList<>();
    this.parent = parent;
    this.tagName = tagName;
  }

  public void parseAndSetAttributes(String attributes) {

    for (int i = 0; i < attributes.length(); i++) {

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
