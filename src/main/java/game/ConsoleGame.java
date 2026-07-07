package game;

import config.GameConfig;
import java.util.Scanner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleGame implements CommandLineRunner {

  private final GameConfig gameConfig;

  public ConsoleGame(GameConfig gameConfig) {
    this.gameConfig = gameConfig;
  }

  @Override
  public void run(String... args) {
    Board board = new Board(gameConfig);
    Scanner scanner = new Scanner(System.in);

    System.out.println("Use W/A/S/D to move");
    System.out.println(board);

    while (true) {
      if (board.isWon()) {
        System.out.println("You win!");
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
      case "w","W" -> Direction.UP;
      case "a","A" -> Direction.LEFT;
      case "s","S" -> Direction.DOWN;
      case "d","D" -> Direction.RIGHT;
      default -> null;
    };
  }
}