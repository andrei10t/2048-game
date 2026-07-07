package game;

import config.AiConfig;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class GameUtil {

  //start a local Ollama server
  //done here instead of comand line so that we can use options easier
  //ofcourse in a real project, environment variables are easier to manage, could set them in the pipeline
  static void startOllama(boolean showLogs) {
    ProcessBuilder builder = new ProcessBuilder("ollama", "serve");
    if (showLogs) {
      builder.redirectOutput(ProcessBuilder.Redirect.INHERIT)
          .redirectError(ProcessBuilder.Redirect.INHERIT);
    } else {
      builder.redirectOutput(ProcessBuilder.Redirect.DISCARD)
          .redirectError(ProcessBuilder.Redirect.DISCARD);
    }
    try {
      builder.start();
      Thread.sleep(3000);
    } catch (IOException exception) {
      System.err.println("<startOllama> could not start Ollama: " + exception.getMessage());
    } catch (InterruptedException exception) {
      Thread.currentThread().interrupt();
    }
  }

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
  static String suggestMove(Board board, AiConfig aiConfig) {
    String prompt = aiConfig.getPromptTemplate().replace("{board}", board.toString());

    String requestBody =
        "{\"model\":\"" + aiConfig.getModel() + "\",\"stream\":false,\"prompt\":\""
            + StringUtil.escapeJson(prompt) + "\"}";

    try {
      Process process =
          new ProcessBuilder("curl", "-s", aiConfig.getUrl(), "-d", requestBody)
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
