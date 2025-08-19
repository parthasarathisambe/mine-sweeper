package com.mine.sweeper.board;

import com.mine.sweeper.cell.Cell;

import java.util.Random;

public class Board {

  private final int rows;

  private final int cols;

  private final int totalMines;

  private final Cell[][] grid;

  public Board(int rows, int cols, int totalMines) {
    this.rows = rows;
    this.cols = cols;
    this.totalMines = totalMines;
    this.grid = new Cell[rows][cols];

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        grid[i][j] = new Cell();
      }
    }
  }

  public void initializeBoard(int startRow, int startCol) {
    placeMines(startRow, startCol);
    calculateAdjacentMines();
  }

  private void placeMines(int startRow, int startCol) {
    Random random = new Random();
    int minesPlaced = 0;

    while (minesPlaced < totalMines) {
      int r = random.nextInt(rows);
      int c = random.nextInt(cols);

      if (r == startRow && c == startCol || grid[r][c].isMine()) {
        continue;
      }

      grid[r][c].setMine(true);
      minesPlaced++;
    }
  }

  private void calculateAdjacentMines() {
    int[] directions = {-1, 0, 1};

    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {

        if (grid[r][c].isMine()) {
          grid[r][c].setAdjacentMines(-1);
          continue;
        }

        int count = 0;
        for (int dr : directions) {
          for (int dc : directions) {

            if (dr == 0 && dc == 0) {
              continue;
            }

            int tr = r + dr;
            int tc = c + dc;

            if (isValidCell(tr, tc) && grid[tr][tc].isMine()) {
              count++;
            }

          }
        }

        grid[r][c].setAdjacentMines(count);
      }
    }
  }

  /**
   * Return true if the cell is safe.
   * Return false if a mine is triggered.
   */
  public boolean revealCell(int row, int col) {
    if (!isValidCell(row, col) || grid[row][col].isRevealed() || grid[row][col].isFlagged()) {
      return true;
    }

    grid[row][col].setRevealed(true);

    if (grid[row][col].isMine()) {
      return false;
    }

    // If no adjacent mines, reveal neighbours (flood fill)
    if (grid[row][col].getAdjacentMines() == 0) {
      int[] directions = {-1, 0, 1};

      for (int dr : directions) {
        for (int dc : directions) {
          if (dr == 0 && dc == 0) {
            continue;
          }

          int tr = row + dr;
          int tc = col + dc;

          if (isValidCell(tr, tc) && !grid[tr][tc].isRevealed()) {
            revealCell(tr, tc);
          }
        }
      }
    }
    return true;
  }

  /**
   * To mark a cell as potentially containing a mine.
   */
  public void toggleFlag(int row, int col) {
    if (isValidCell(row, col) && !grid[row][col].isRevealed()) {
      grid[row][col].setFlagged(!grid[row][col].isFlagged());
    }
  }

  public Cell getCell(int row, int col) {
    if (isValidCell(row, col)) {
      return grid[row][col];
    }
    return null;
  }

  public boolean isValidCell(int row, int col) {
    return row >= 0 && row < rows && col >= 0 && col < cols;
  }

  public int getRows() {
    return rows;
  }

  public int getCols() {
    return cols;
  }

}
