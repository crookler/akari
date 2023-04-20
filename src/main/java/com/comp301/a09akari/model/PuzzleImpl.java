package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
  int[][] board;

  public PuzzleImpl(int[][] board) {
    this.board = board;
  }

  public int getWidth() {
    return board[0].length;
  }

  public int getHeight() {
    return board.length;
  }

  public CellType getCellType(int r, int c) {
    if (!inBounds(r, c)) {
      throw new IndexOutOfBoundsException();
    }

    int cell = board[r][c];

    if (cell >= 0 && cell <= 4) {
      return CellType.CLUE;
    } else if (cell == 5) {
      return CellType.WALL;
    } else if (cell == 6) {
      return CellType.CORRIDOR;
    } else {
      throw new IllegalStateException("Malformed board");
    }
  }

  public int getClue(int r, int c) {
    if (getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }

    return board[r][c];
  }

  private boolean inBounds(int r, int c) {
    return r < getHeight() && c < getWidth() && r >= 0 && c >= 0;
  }
}
