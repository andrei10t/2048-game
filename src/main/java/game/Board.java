package game;

import config.GameConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Board {

  private final Integer[][] grid;
  private int winValue;
  private double fourSpawnProbability;

  public Board(GameConfig config) {
    this.grid = new Integer[config.getBoardSize()][config.getBoardSize()];
    this.winValue = config.getWinValue();
    this.fourSpawnProbability = config.getFourSpawnProbability();
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

  //return whether move produced a change
  public boolean move(Direction direction) {
    Integer[][] before = snapshotGrid();

    switch (direction) {
      case LEFT -> collapseAllRowsLeft();
      case RIGHT -> {
        reverseAllRows();
        collapseAllRowsLeft();
        reverseAllRows();
      }
      case UP -> {
        transpose();
        collapseAllRowsLeft();
        transpose();
      }
      case DOWN -> {
        transpose();
        reverseAllRows();
        collapseAllRowsLeft();
        reverseAllRows();
        transpose();
      }
    }
    boolean isChanged = isChangedGrid(before);
    if (isChanged) {
      spawnRandomTile();
    }
    return isChanged;
  }

  //drop a 2/4 in a random empty cell
  private void spawnRandomTile() {
    List<List<Integer>> availableEmptyCells = new ArrayList<>();
    for (int row = 0; row < grid.length; row++) {
      for (int column = 0; column < grid[row].length; column++) {
        if (grid[row][column] == null) {
          availableEmptyCells.add(List.of(row, column));
        }
      }
    }

    if (availableEmptyCells.isEmpty()) {
      return;
    }

    List<Integer> cellToSpawn =
        availableEmptyCells.get(ThreadLocalRandom.current().nextInt(availableEmptyCells.size()));
    grid[cellToSpawn.get(0)][cellToSpawn.get(1)] =
        ThreadLocalRandom.current().nextDouble() < fourSpawnProbability ? 4 : 2;
  }

  //deep clone the grid
  //TODO can be done nicer
  private Integer[][] snapshotGrid() {
    Integer[][] copy = new Integer[grid.length][];
    for (int row = 0; row < grid.length; row++) {
      copy[row] = grid[row].clone();
    }
    return copy;
  }

  //are two grids holding the same values?
  private boolean isChangedGrid(Integer[][] before) {
    for (int row = 0; row < grid.length; row++) {
      for (int column = 0; column < grid[row].length; column++) {
        if (!Objects.equals(before[row][column], grid[row][column])) {
          return true;
        }
      }
    }
    return false;
  }

  //every move is a collapse left if reversed+transposed
  private void collapseAllRowsLeft() {
    for (Integer[] row : grid) {
      Integer[] collapsed = collapseRowLeft(row);
      System.arraycopy(collapsed, 0, row, 0, collapsed.length);
    }
  }

  // [null 8 2 2] -> [8 4 null null]
  // [2 2 2 2] -> [ 4 4 null null]
  private Integer[] collapseRowLeft(Integer[] row) {
    List<Integer> valuesToMerge = new ArrayList<>();
    for (Integer cell : row) {
      if (cell != null) {
        valuesToMerge.add(cell);
      }
    }

    List<Integer> merged = new ArrayList<>();
    for (int index = 0; index < valuesToMerge.size(); index++) {
      boolean canMerge =
          index + 1 < valuesToMerge.size() && valuesToMerge.get(index).equals(valuesToMerge.get(index + 1));
      if (canMerge) {
        merged.add(valuesToMerge.get(index) * 2);
        index++; // skip the tile we just merged
      } else {
        merged.add(valuesToMerge.get(index));
      }
    }

    return merged.toArray(new Integer[row.length]);
  }

  private void transpose() {
    for (int row = 0; row < grid.length; row++) {
      for (int column = row + 1; column < grid[row].length; column++) {
        Integer temp = grid[row][column];
        grid[row][column] = grid[column][row];
        grid[column][row] = temp;
      }
    }
  }

  private void reverseAllRows() {
    for (Integer[] row : grid) {
      for (int left = 0, right = row.length - 1; left < right; left++, right--) {
        Integer temp = row[left];
        row[left] = row[right];
        row[right] = temp;
      }
    }
  }
}
