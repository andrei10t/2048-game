import config.GameConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Board {

  private final Integer[][] grid;
  private int winValue;

  public Board(GameConfig config) {
    this.grid = new Integer[config.getBoardSize()][config.getBoardSize()];
    this.winValue = config.getWinValue();
    setupBoard();
  }

  //testing purposes
  public Board(Integer[][] grid, int winValue) {
    this.grid = grid;
    this.winValue = winValue;
  }

  private void setupBoard() {
    //create collections of cells (0,0),(0,1)...
    List<List<Integer>> cells = new ArrayList<>();
    for (int row = 0; row < grid.length; row++) {
      for (int column = 0; column < grid[row].length; column++) {
        cells.add(List.of(row, column));
      }
    }

    Collections.shuffle(cells);

    //pick a random number(requirement)
    //assumption: number is from 1 to all cells possible.
    int twoCount = ThreadLocalRandom.current().nextInt(1, cells.size() + 1);

    //first twoCount cells in random order get a 2.
    for (int index = 0; index < twoCount; index++) {
      List<Integer> cell = cells.get(index);
      grid[cell.get(0)][cell.get(1)] = 2;
    }
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
