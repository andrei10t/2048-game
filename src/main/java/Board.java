import config.GameConfig;

public class Board {

  private final Integer[][] grid;

  public Board(GameConfig config) {
    this.grid = new Integer[config.getBoardSize()][config.getBoardSize()];
  }

  public Board(Integer[][] grid) {
    this.grid = grid;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (Integer[] row : grid) {
      builder.append('[');
      for (int column = 0; column < row.length; column++) {
        builder.append(row[column] == null ? "null" : row[column].toString());
        if (column < row.length - 1) {
          builder.append(", ");
        }
      }
      builder.append("]\n");
    }
    return builder.toString();
  }

  public boolean isWon(){
    return false;
  }

  public boolean isGameOver(){
    return false;
  }

  public Board move(){
    return null;
  }
}
