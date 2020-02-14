package net.lliira.sudoku.solver;

public class BrutalForceSolver extends SudokuSolver {

  private static class Cell {
    private int x;
    private int y;

    private Cell(int x, int y) {
      this.x = x;
      this.y = y;
    }

    private boolean next() {
      if (x == SIZE - 1) {
        if (y == SIZE - 1) return false ;
        x =0;
        y++;
      } else  x++;
      return true;
    }

    public boolean previous() {
      if (x == 0) {
        if (y == 0) return false;
        x = SIZE -1;
        y--;
      } else x--;
      return true;
    }

    @Override
    public String toString() {
      return String.format("(%d,%d)", x, y);
    }
  }

  private final boolean recursive;

  public BrutalForceSolver(final int[][] board, boolean recursive) {
    super(board);
    this.recursive = recursive;
  }

  @Override
  public boolean solve() {
    return recursive ? solveRecursively() : solveLinearly();
  }

  private boolean solveRecursively() {
    for (int row = 0; row < SIZE; row++) {
      for (int col = 0; col < SIZE; col++) {
        if (board[row][col] == EMPTY) {
          for (int number = 1; number <= SIZE; number++) {
            if (isOk(row, col, number)) {
              board[row][col] = number;

              if (solveRecursively()) {
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

  private boolean solveLinearly() {
    boolean[][] mask = new boolean[SIZE][SIZE];
    for (int row = 0; row < SIZE; row++) {
      for (int col = 0; col < SIZE; col++) {
        mask[row][col] = board[row][col] != EMPTY;
      }
    }
    Cell cell = new Cell(0, 0);
    boolean hasCell = true;
    boolean forward = true;
    int number = 1;
    while (hasCell) {
      if (mask[cell.y][cell.x]) {
        hasCell = forward ? cell.next() : cell.previous();
        continue;
      }

      if (!forward) {
        number = board[cell.y][cell.x] + 1;
        board[cell.y][cell.x] = EMPTY;
        forward = true;
      } else if (number > SIZE) {
        forward = false;
        hasCell = cell.previous();
      } else if (isOk(cell.y, cell.x, number)) {
        board[cell.y][cell.x] = number;
        hasCell = cell.next();
        number = 1;
      } else {
        number++;
      }
    }
    return forward;
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
