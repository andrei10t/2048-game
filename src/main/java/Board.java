import config.GameConfig;

public class Board {

  private final Integer[][] grid;
  private int winValue;

  public Board(GameConfig config) {
    this.grid = new Integer[config.getBoardSize()][config.getBoardSize()];
    this.winValue = config.getWinValue();
  }

  //testing purposes
  public Board(Integer[][] grid, int winValue) {
    this.grid = grid;
    this.winValue = winValue;
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

  public boolean isWon() {
    for (Integer[] row : grid) {
      for (Integer cell : row) {
        if (cell != null && cell == winValue) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean hasAvailableMoves() {
    for (int row = 0; row < grid.length; row++) {
      for (int column = 0; column < grid[row].length; column++) {
        Integer cell = grid[row][column];

        // an empty cell means another number can appear
        if (cell == null) {
          return true;
        }

        // neighbours can be merged
        boolean equalRightNeighbour = column + 1 < grid[row].length && cell.equals(grid[row][column + 1]);
        boolean equalDownNeighbour = row + 1 < grid.length && cell.equals(grid[row + 1][column]);
        if (equalRightNeighbour || equalDownNeighbour) return true;
      }
    }
    return false;
  }

  public boolean isGameOver() {
    return !hasAvailableMoves();
  }

  public Board move() {
    return null;
  }
}
