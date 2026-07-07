package game;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

  private static final Pattern RESPONSE_FIELD = Pattern.compile("\"response\"\\s*:\\s*\"(.*?)\"");

  public static String escapeJson(String text) {
    return text.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
  }

  public static String extractResponse(String json) {
    Matcher matcher = RESPONSE_FIELD.matcher(json);
    return matcher.find() ? matcher.group(1).trim() : null;
  }
}
