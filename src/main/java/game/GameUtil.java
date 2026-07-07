package game;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class GameUtil {
  static void partyParrot() {
    try {
      new ProcessBuilder("curl", "parrot.live")
          .inheritIO()
          .start()
          .waitFor();
    } catch (IOException | InterruptedException exception) {
      Thread.currentThread().interrupt();
    }
  }

  // ask a locally running Ollama for the best next move
  static String suggestMove(Board board, String url, String model, String promptTemplate) {
    String prompt = promptTemplate.replace("{board}", board.toString());

    String requestBody =
        "{\"model\":\"" + model + "\",\"stream\":false,\"prompt\":\""
            + StringUtil.escapeJson(prompt) + "\"}";

    try {
      Process process =
          new ProcessBuilder("curl", "-s", url, "-d", requestBody)
              .redirectErrorStream(true)
              .start();
      String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
      process.waitFor();
      return StringUtil.extractResponse(output);
    } catch (IOException exception) {
      System.err.println("<suggestMove> AI not available " + exception.getMessage());
      return null;
    } catch (InterruptedException exception) {
      Thread.currentThread().interrupt();
      return null;
    }
  }
}
