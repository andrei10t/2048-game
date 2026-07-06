import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import config.GameConfig;
import org.junit.jupiter.api.Test;

class BoardTest {

  final Integer[][] wonGame = {
      {null, 8, 2, 2},
      {4, 2, 2048, 2},
      {null, null, null, null},
      {null, null, null, 2}
  };

  final Integer[][] ongoingGame = {
      {null, 8, 2, 2},
      {4, 2, 16, 2},
      {null, null, null, null},
      {null, null, null, 2}
  };


  final Integer[][] gameOverGame = {
      {2, 8, 2, 4},
      {4, 2, 16, 2},
      {1024, 512, 32, 8},
      {512, 8, 16, 2}
  };





  @Test
  void rendersBoard() {

    Board board = new Board(wonGame,2048);
    String rendered = board.toString();

    assertEquals("[null, 8, 2, 2]", rendered.lines().toList().get(0));
    assertEquals("[4, 2, 2048, 2]", rendered.lines().toList().get(1));
    assertEquals("[null, null, null, null]", rendered.lines().toList().get(2));
    assertEquals("[null, null, null, 2]", rendered.lines().toList().get(3));
  }

  @Test
  void testGameStatus(){
    Board board1 = new Board(wonGame,2048);
    Board board2 = new Board(ongoingGame,2048);
    Board board3 = new Board(gameOverGame,2048);

    assertTrue(board1.isWon());
    assertFalse(board2.isWon());
    assertFalse(board3.isWon());

    assertTrue(board3.isGameOver());
    assertFalse(board2.isGameOver());
    assertFalse(board1.isGameOver());
  }

  @Test
  void testSetupBoard(){
    Board board = new Board(new GameConfig(4, 2048, 0.1));
    System.out.println(board);
  }
}
