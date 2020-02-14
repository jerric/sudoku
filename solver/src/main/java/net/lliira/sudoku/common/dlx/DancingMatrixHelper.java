package net.lliira.sudoku.common.dlx;

import java.util.Arrays;
import java.util.List;

public class DancingMatrixHelper {
  // 4 constraints : cell, line, column, boxes
  private static final int CONSTRAINTS = 4;
  // Starting index for cover matrix
  private static final int COVER_START_INDEX = 1;
  private static final int MIN_VALUE = 1;
  private static final int EMPTY = 0;

  private final int size;
  private final int box;
  private final int maxValue;

  public DancingMatrixHelper(int size, int box) {
    this.size = size;
    this.box = box;
    this.maxValue = size;
  }

  // Converting Sudoku grid as a cover matrix
  public int[][] convertInCoverMatrix(int[][] grid) {
    int[][] coverMatrix = createCoverMatrix();

    // Taking into account the values already entered in Sudoku's grid instance
    for (int row = COVER_START_INDEX; row <= size; row++) {
      for (int column = COVER_START_INDEX; column <= size; column++) {
        int n = grid[row - 1][column - 1];

        if (n != EMPTY) {
          for (int num = MIN_VALUE; num <= maxValue; num++) {
            if (num != n) {
              Arrays.fill(coverMatrix[indexInCoverMatrix(row, column, num)], 0);
            }
          }
        }
      }
    }
    return coverMatrix;
  }

  public int[][] convertDLXListToGrid(List<DancingNode> answer) {
    int[][] result = new int[size][size];

    for (DancingNode n : answer) {
      DancingNode rcNode = n;
      int min = Integer.parseInt(rcNode.column.name);

      for (DancingNode tmp = n.right; tmp != n; tmp = tmp.right) {
        int val = Integer.parseInt(tmp.column.name);

        if (val < min) {
          min = val;
          rcNode = tmp;
        }
      }

      // we get line and column
      int ans1 = Integer.parseInt(rcNode.column.name);
      int ans2 = Integer.parseInt(rcNode.right.column.name);
      int r = ans1 / size;
      int c = ans1 % size;
      // and the affected value
      int num = (ans2 % size) + 1;
      // we affect that on the result grid
      result[r][c] = num;
    }
    return result;
  }


  public void print(int[][] grid) {
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        System.out.print(grid[row][col]);
        System.out.print(", ");
      }
      System.out.println();
    }
  }


  // Building of an empty cover matrix
  private int[][] createCoverMatrix() {
    int[][] coverMatrix = new int[size * size * maxValue][size * size * CONSTRAINTS];

    int header = 0;
    header = createCellConstraints(coverMatrix, header);
    header = createRowConstraints(coverMatrix, header);
    header = createColumnConstraints(coverMatrix, header);
    header = createBoxConstraints(coverMatrix, header);

    return coverMatrix;
  }

  // Index in the cover matrix
  private int indexInCoverMatrix(int row, int column, int num) {
    return (row - 1) * size * size + (column - 1) * size + (num - 1);
  }

  private int createBoxConstraints(int[][] matrix, int header) {
    for (int row = COVER_START_INDEX; row <= size; row += box) {
      for (int column = COVER_START_INDEX; column <= size; column += box) {
        for (int n = COVER_START_INDEX; n <= size; n++, header++) {
          for (int rowDelta = 0; rowDelta < box; rowDelta++) {
            for (int columnDelta = 0; columnDelta < box; columnDelta++) {
              int index = indexInCoverMatrix(row + rowDelta, column + columnDelta, n);
              matrix[index][header] = 1;
            }
          }
        }
      }
    }
    return header;
  }

  private int createColumnConstraints(int[][] matrix, int header) {
    for (int column = COVER_START_INDEX; column <= size; column++) {
      for (int n = COVER_START_INDEX; n <= size; n++, header++) {
        for (int row = COVER_START_INDEX; row <= size; row++) {
          int index = indexInCoverMatrix(row, column, n);
          matrix[index][header] = 1;
        }
      }
    }
    return header;
  }

  private int createRowConstraints(int[][] matrix, int header) {
    for (int row = COVER_START_INDEX; row <= size; row++) {
      for (int n = COVER_START_INDEX; n <= size; n++, header++) {
        for (int column = COVER_START_INDEX; column <= size; column++) {
          int index = indexInCoverMatrix(row, column, n);
          matrix[index][header] = 1;
        }
      }
    }
    return header;
  }

  private int createCellConstraints(int[][] matrix, int header) {
    for (int row = COVER_START_INDEX; row <= size; row++) {
      for (int column = COVER_START_INDEX; column <= size; column++, header++) {
        for (int n = COVER_START_INDEX; n <= size; n++) {
          int index = indexInCoverMatrix(row, column, n);
          matrix[index][header] = 1;
        }
      }
    }
    return header;
  }
}
