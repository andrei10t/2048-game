package game;

import java.io.IOException;

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
}
