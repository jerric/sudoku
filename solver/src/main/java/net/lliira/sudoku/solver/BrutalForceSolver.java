package net.lliira.sudoku.solver;

public class BrutalForceSolver implements SudokuSolver {
  private static final int SIZE = 9;
  private static final int BOX = 3;
  private static final int EMPTY = 0;

  private final int[][] board;

  public BrutalForceSolver(final int[][] board) {
    this.board = board;
  }

  @Override
  public boolean solve() {
    for (int row = 0; row < SIZE; row++) {
      for (int col = 0; col < SIZE; col++) {
        if (board[row][col] == EMPTY) {
          for (int number = 1; number <= SIZE; number++) {
            if (isOk(row, col, number)) {
              board[row][col] = number;

              if (solve()) {
                return true;
              } else {
                board[row][col] = EMPTY;
              }
            }
          }

          return false;
        }
      }
    }

    return true;
  }

  @Override
  public void print() {
    for (int row = 0; row < SIZE; row++) {
      for (int col = 0; col < SIZE; col++) {
        System.out.print(board[row][col]);
        System.out.print(", ");
      }
      System.out.println();
    }
  }

  private boolean isOk(int row, int col, int number) {
    return !isInRow(row, number) && !isInCol(col, number) && !isInBox(row, col, number);
  }

  private boolean isInRow(int row, int number) {
    for (int i = 0; i < SIZE; i++) {
      if (board[row][i] == number) return true;
    }

    return false;
  }

  private boolean isInCol(int col, int number) {
    for (int i = 0; i < SIZE; i++) {
      if (board[i][col] == number) return true;
    }

    return false;
  }

  private boolean isInBox(int row, int col, int number) {
    int r = row - row % BOX;
    int c = col - col % BOX;

    for (int i = r; i < r + BOX; i++) {
      for (int j = c; j < c + BOX; j++) {
        if (board[i][j] == number) return true;
      }
    }
    return false;
  }
}
