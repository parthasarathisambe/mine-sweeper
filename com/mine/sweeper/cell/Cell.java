package com.mine.sweeper.cell;

public class Cell {

  private int adjacentMines;

  private boolean isFlagged;

  private boolean isRevealed;

  private boolean isMine;

  public int getAdjacentMines() {
    return adjacentMines;
  }

  public void setAdjacentMines(int adjacentMines) {
    this.adjacentMines = adjacentMines;
  }

  public boolean isFlagged() {
    return isFlagged;
  }

  public void setFlagged(boolean flagged) {
    isFlagged = flagged;
  }

  public boolean isRevealed() {
    return isRevealed;
  }

  public void setRevealed(boolean revealed) {
    isRevealed = revealed;
  }

  public boolean isMine() {
    return isMine;
  }

  public void setMine(boolean mine) {
    isMine = mine;
  }
}
