package com.mine.sweeper.game;

import com.mine.sweeper.board.Board;
import com.mine.sweeper.cell.Cell;

public class Game {

  private final Board board;

  private GameState gameState;

  private int movesCount;

  private boolean gameOver;

  public Game(int rows, int cols, int totalMines) {
    this.gameState = GameState.READY;
    this.board = new Board(rows, cols, totalMines);
    this.movesCount = 0;
  }

  public void startGame(int startRow, int startCol) {
    board.initializeBoard(startRow, startCol);
    makeMove(startRow, startCol);
  }

  public void makeMove(int row, int col) {
    if (gameOver) {
      System.out.print("Game Over! No further moves allowed.");
      return;
    }

    if (board.getCell(row, col).isFlagged()) {
      System.out.print("Cannot make moves on Flagged cell");
      return;
    }

    if (!board.isValidCell(row, col)) {
      System.out.print("Invalid cell.");
      return;
    }

    boolean safe = board.revealCell(row, col);
    movesCount++;

    if (!safe) {
      gameOver = true;
      gameState = GameState.LOSE;
      System.out.print("Pff..! You Lost.");
    } else if (checkWin()) {
      gameOver = true;
      gameState = GameState.WIN;
      System.out.print("Wohoo..! You Won.");
    } else {
      gameState = GameState.IN_PROGRESS;
    }
  }

  private boolean checkWin() {
    for (int r = 0; r < board.getRows(); r++) {
      for (int c = 0; c < board.getCols(); c++) {
        Cell cell = board.getCell(r, c);
        if (!cell.isMine() && !cell.isRevealed()) {
          return false;
        }
      }
    }
    return true;
  }

  public void toggleFlag(int row, int col) {
    if (!gameOver) {
      board.toggleFlag(row, col);
    }
  }

  public Board getBoard() {
    return board;
  }

  public GameState getGameState() {
    return gameState;
  }

  public boolean isGameOver() {
    return gameOver;
  }

  public int getMovesCount() {
    return movesCount;
  }

}
