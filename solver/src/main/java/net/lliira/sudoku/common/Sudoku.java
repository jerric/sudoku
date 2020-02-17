package net.lliira.sudoku.common;

public class Sudoku {
  public static final int SIZE = 9;
  public static final int BOX = 3;
  private static final char NEW_ROW = ';';
  private static final int EMPTY = 0;

  private int degree;
  private final int[][] board = new int[SIZE][SIZE];

  public Sudoku(int[][] board) {
    this.degree = copy(board, this.board);
  }

  public Sudoku(String board) {
    int row = 0, col = 0, degree = 0;
    for (char cell : board.toCharArray()) {
      if (cell == NEW_ROW) {
        row++;
        col = 0;
      } else {
        this.board[row][col] = Integer.parseInt(Character.toString(cell));
        if (this.board[row][col] != EMPTY) degree++;
        col++;
      }
    }
    this.degree = degree;
  }

  public int degree() {
    return degree;
  }

  public Sudoku copy(){
    return new Sudoku(board);
  }

  public int[][] get() {
    int[][] board = new int[SIZE][SIZE];
    copy(this.board, board);
    return board;
  }

  public int get(int row, int col) {
    return board[row][col];
  }

  public boolean isEmpty(int row, int col) {
    return board[row][col] == EMPTY;
  }

  public boolean set(int row, int col, int number){
    assert number >= 1 && number <= 9;
    if (isOk(row, col, number)) {
      board[row][col] = number;
      degree++;
      return true;
    } else return false;
  }

  public void unset(int row, int col) {
    if (!isEmpty(row, col)) {
      board[row][col] = EMPTY;
      degree--;
    }
  }

  private int copy(int[][] source, int[][] dest) {
    int degree = 0;
    for (int row = 0; row < SIZE; row++) {
      for (int col = 0; col < SIZE; col++) {
        dest[row][col] = source[row][col];
        if (dest[row][col] != EMPTY) degree++;
      }
    }
    return degree;
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

  @Override
  public int hashCode() {
    int code = 0;
    for (int row = 0; row < SIZE; row++) {
      for (int col = 0; col < SIZE; col++) {
        code ^= board[row][col];
      }
    }
    return code;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Sudoku) {
      Sudoku sudoku = (Sudoku) obj;
      boolean equal = true;
      for (int row = 0; row < SIZE; row++) {
        for (int col = 0; col < SIZE; col++) {
          equal = board[row][col] == sudoku.board[row][col];
          if (!equal) break;
        }
        if (!equal) break;
      }
      return equal;
    }
    return false;
  }
}
