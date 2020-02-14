package net.lliira.sudoku.finder;

import java.util.ArrayList;
import java.util.List;

public abstract class SudokuFinder {
  protected static final int SIZE = 9;
  protected static final int BOX = 3;
  protected static final int EMPTY = 0;

  protected int[][] board;
  protected List<int[][]> solutions;

  public SudokuFinder(int[][] board) {
    this.board = board;
    solutions = new ArrayList<>();
  }

  public abstract List<int[][]> find();

  public int[][] getBoard() {
    return board;
  }

  public List<int[][]> getSolutions() {
    return solutions;
  }
}
