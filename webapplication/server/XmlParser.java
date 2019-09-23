package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class XmlParser {

  private Deque<String> checkXml = new ArrayDeque<>();

  private final char OPEN_ANGLE = '<';

  private final char DECLARE_CHAR = '?';

  private final String END_TAG = "</";

  private final char CLOSE_ANGLE = '>';

  private static char WHITE_SPACE = ' ';

  private Xml root = new Xml(null, "");

  private Xml iterator = root;


  public String getFiles(BufferedReader reader) throws IOException {

    String header = "";
    StringBuilder xml = new StringBuilder();
    String line = "";
    if ((header = reader.readLine()) != null) {
      // 헤더분석

      // xml 통채로 저장
      while ((line = reader.readLine()) != null) {
        xml.append(line);
      }
    }
    return xml.toString();
  }

  public Xml parse(String xmlData, int startIdx, int endIdx) {

    int pos = startIdx;
    StringBuilder data = new StringBuilder();
    boolean isTagName = true;

    while (startIdx < endIdx) {
      isTagName = true;

      // tag
      if (xmlData.charAt(startIdx) == OPEN_ANGLE) {
        StringBuilder startTag = new StringBuilder();
        StringBuilder attribute = new StringBuilder();
        StringBuilder endTag = new StringBuilder(END_TAG);
        int nextStartIdx = 0;
        int nextEndIdx = 0;
        // open Tag
        for (pos = startIdx + 1; pos < xmlData.length(); pos++) {
          if (isTagName && xmlData.charAt(pos) == WHITE_SPACE) {
            isTagName = false;
            continue;
          }
          if (xmlData.charAt(pos) == CLOSE_ANGLE) {
            nextStartIdx = pos + 1;
            break;
          }

          if (isTagName) {
            startTag.append(xmlData.charAt(pos));
          } else {
            attribute.append(xmlData.charAt(pos));
          }
        }

        System.out.println("start : " + startTag.toString());
        endTag.append(startTag).append('>');
        System.out.println("end : " + endTag.toString());
        System.out.println("attribute : " + attribute.toString());

        int endTagLength = endTag.length();
        // end Tag 찾기
        for (pos = pos + 1; pos < endIdx - endTagLength; pos++) {
          if (xmlData.substring(pos, pos + endTagLength).equals(endTag.toString())) {
            Xml subXml = new Xml(iterator, startTag.toString());

            data = new StringBuilder();
            iterator.getSubTag().add(subXml);
            iterator = subXml;
            nextEndIdx = pos;
            parse(xmlData, nextStartIdx, nextEndIdx);
            startIdx = pos + endTagLength;
            break;
          }
        }
      } else {
        data.append(xmlData.charAt(startIdx));
      }
      startIdx++;
    }

    iterator.setValue(data.toString());
    data = null;

    iterator = iterator.getParent();
    return root;
  }

}
