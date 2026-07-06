import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BoardTest {

  @Test
  void rendersBoard() {
    Integer[][] grid = {
      {null, 8, 2, 2},
      {4, 2, 2048, 2},
      {null, null, null, null},
      {null, null, null, 2}
    };

    Board board = new Board(grid);
    String rendered = board.toString();

    assertEquals("[null, 8, 2, 2]", rendered.lines().toList().get(0));
    assertEquals("[4, 2, null, 2]", rendered.lines().toList().get(1));
    assertEquals("[null, null, null, null]", rendered.lines().toList().get(2));
    assertEquals("[null, null, null, 2]", rendered.lines().toList().get(3));
  }
}
