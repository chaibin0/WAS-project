package server;

import java.io.BufferedReader;
import java.io.IOException;

public class XmlParser {

  private static final char OPEN_ANGLE = '<';

  @SuppressWarnings("unused")
  private static final char DECLARE_CHAR = '?';

  private static final String END_TAG = "</";

  private static final char CLOSE_ANGLE = '>';

  private static final char WHITE_SPACE = ' ';

  private Xml root = new Xml(null, "");

  private Xml iterator = root;

  @SuppressWarnings("unused")
  private String header = "";

  /**
   * 문자열 입력 스트림을 통해서 xml을 String에 저장한다.
   * 
   * @param reader 문자열 입력 스트림
   * @return xml이 저장된 문자열
   * @throws IOException IOException
   */
  public String getFiles(BufferedReader reader) throws IOException {

    StringBuilder xml = new StringBuilder();
    String line = "";
    // xml 선언문
    if ((header = reader.readLine()) != null) {
      // xml 통채로 저장
      while ((line = reader.readLine()) != null) {
        xml.append(line);
      }
    }
    return xml.toString();
  }

  /**
   * Xml파일을 파싱한다. 재귀함수를 통해 파싱이 된다.
   * 
   * @param xmlData xml 문서
   * @param startIdx 시작 위치
   * @param endIdx 끝 위치
   * @return Xml를 반환한다.
   */
  public Xml parse(String xmlData, int startIdx, int endIdx) {

    int pos = startIdx;
    StringBuilder data = new StringBuilder();
    StringBuilder attribute = new StringBuilder();
    boolean isTagName = true;
    while (startIdx < endIdx) {
      isTagName = true;
      // tag
      if (xmlData.charAt(startIdx) == OPEN_ANGLE) {
        StringBuilder startTag = new StringBuilder();
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

        StringBuilder endTag = new StringBuilder(END_TAG);
        endTag.append(startTag).append('>');
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
    iterator.parseAndSetAttributes(attribute.toString());
    iterator.setValue(data.toString());
    iterator = iterator.getParent();
    return root;
  }

}
