package net.lliira.sudoku.common;

public class Sudoku {
  public static final int SIZE = 9;
  private static final char NEW_ROW = ';';
  private static final int EMPTY = 0;

  private final int[][] board = new int[SIZE][SIZE];

  public Sudoku(int[][] board) {
    for (int row = 0; row < SIZE; row++) {
      for (int col = 0; col < SIZE; col++) {
        this.board[row][col] = board[row][col];
      }
    }
  }

  public Sudoku(String board) {
    int row = 0, col = 0;
    for (char cell : board.toCharArray()) {
      if (cell == NEW_ROW) {
        row++;
        col = 0;
      } else {
        this.board[row][col] = Integer.parseInt(Character.toString(cell));
        col++;
      }
    }
  }

  public int[][] get() {
    return board;
  }

  public void flip() {
    for (int row = 0; row < SIZE; row++) {
      for (int col = 0; col < SIZE / 2; col++) {
        int temp = board[row][col];
        board[row][col] = board[row][SIZE - col - 1];
        board[row][SIZE - col - 1] = temp;
      }
    }
  }

  public void rotate() {
    // Consider all squares one by one
    for (int x = 0; x < SIZE / 2; x++) {
      // Consider elements in group of 4 in
      // current square
      for (int y = x; y < SIZE - x - 1; y++) {
        // store current cell in temp variable
        int temp = board[x][y];

        // move values from right to top
        board[x][y] = board[y][SIZE - 1 - x];

        // move values from bottom to right
        board[y][SIZE - 1 - x] = board[SIZE - 1 - x][SIZE - 1 - y];

        // move values from left to bottom
        board[SIZE - 1 - x][SIZE - 1 - y] = board[SIZE - 1 - y][x];

        // assign temp to left
        board[SIZE - 1 - y][x] = temp;
      }
    }
  }

  public void print() {
    for (int row = 0; row < SIZE; row++) {
      System.out.print("{");
      for (int col = 0; col < SIZE; col++) {
        if (col != 0) System.out.print(", ");

        System.out.print(board[row][col]);
      }
      System.out.println("},");
    }
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int row = 0; row < SIZE; row++) {
      // find the last non-empty cell in the row.
      int last = SIZE - 1;
      while (last >= 0 && board[row][last] == EMPTY) {
        last--;
      }
      for (int col = 0; col <= last; col++) {
        builder.append(Integer.toString(board[row][col]));
      }
      builder.append(NEW_ROW);
    }
    // remove the trailing NEW_ROWs, they are no needed.
    while (builder.charAt(builder.length() - 1) == NEW_ROW) {
      builder.deleteCharAt(builder.length() - 1);
    }
    return builder.toString();
  }
}
