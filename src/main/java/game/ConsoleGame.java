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
    
    System.out.println("Use W/A/S/D to move, 'h' for an AI hint, 'q' to quit");
    System.out.println(board);

    while (!board.isWon() && !board.isGameOver()) {
      System.out.print("Move: ");
      if (!scanner.hasNextLine()) {
        break;
      }
      String input = scanner.nextLine().trim();
      if (input.equalsIgnoreCase("q")) {
        break;
      }

      if (input.equalsIgnoreCase("h")) {
        String suggestion = suggestMove(board, aiConfig);
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

    if (board.isWon()) {
      partyParrot();
    } else if (board.isGameOver()) {
      System.out.println("Better luck next time!");
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