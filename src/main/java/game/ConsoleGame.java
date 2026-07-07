package game;

import static game.GameUtil.partyParrot;
import static game.GameUtil.startOllama;
import static game.GameUtil.suggestMove;

import config.AiConfig;
import config.GameConfig;
import java.time.LocalDateTime;
import java.util.Scanner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleGame implements CommandLineRunner {

  private final GameConfig gameConfig;
  private final AiConfig aiConfig;

  public ConsoleGame(GameConfig gameConfig, AiConfig aiConfig) {
    this.gameConfig = gameConfig;
    this.aiConfig = aiConfig;
  }

  @Override
  public void run(String... args) {
    startOllama(aiConfig.isShowLogs());
    Board board = new Board(gameConfig);
    Scanner scanner = new Scanner(System.in);
    //how much time did we play this session
    LocalDateTime startTime = LocalDateTime.now();

    System.out.println("Use W/A/S/D to move, 'h' for an AI hint, 'q' to quit");
    System.out.println(board);

    while (true) {
      if (board.isWon()) {
        partyParrot();
        break;
      }
      if (board.isGameOver()) {
        System.out.println("Better luck next time!");
        break;
      }

      System.out.print("Move: ");
      if (!scanner.hasNextLine()) {
        break;
      }
      String input = scanner.nextLine().trim();
      if (input.equals("q") || input.equals("Q")) {
        break;
      }

      if (input.equals("h") || input.equals("H")) {
        String suggestion =
            suggestMove(board, aiConfig.getUrl(), aiConfig.getModel(), aiConfig.getPromptTemplate());
        System.out.println(
            suggestion == null ? "AI hint unavailable" : "AI suggests: " + suggestion);
        continue;
      }

      Direction direction = toDirection(input);
      if (direction == null) {
        System.out.println("Use W/A/S/D to move");
        continue;
      }
      board.move(direction);

      System.out.println(board);
    }
  }

  private Direction toDirection(String input) {
    return switch (input) {
      case "w", "W" -> Direction.UP;
      case "a", "A" -> Direction.LEFT;
      case "s", "S" -> Direction.DOWN;
      case "d", "D" -> Direction.RIGHT;
      default -> null;
    };
  }
}