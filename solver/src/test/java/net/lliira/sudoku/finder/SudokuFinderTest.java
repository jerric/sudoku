package net.lliira.sudoku.finder;

import net.lliira.sudoku.common.Sudoku;
import net.lliira.sudoku.common.dlx.DancingMatrixHelper;
import org.junit.Assert;
import org.junit.Test;

public class SudokuFinderTest {
  private static Sudoku input() {
    return new Sudoku(
        new int[][] {
          {8, 0, 0, 0, 0, 0, 0, 0, 0},
          {0, 0, 3, 6, 0, 0, 0, 0, 0},
          {0, 7, 0, 0, 9, 0, 2, 0, 0},
          {0, 5, 0, 0, 0, 0, 0, 0, 0},
          {0, 0, 0, 0, 4, 5, 7, 0, 0},
          {0, 0, 0, 1, 0, 0, 0, 3, 0},
          {0, 0, 1, 0, 0, 0, 0, 6, 8},
          {0, 0, 8, 5, 0, 0, 0, 1, 0},
          {0, 9, 0, 0, 0, 0, 4, 0, 0}
        });
  }

  @Test
  public void testFindAll() {
    var finder = new DancingLinksFinder();
    var solutions = finder.find(input());
    Assert.assertEquals(434, solutions.size());
  }

  @Test
  public void testFindThree() {
    var finder = new DancingLinksFinder();
    var solutions = finder.find(input(), 3);
    Assert.assertEquals(3, solutions.size());
    int count = 1;
    DancingMatrixHelper matrixHelper = new DancingMatrixHelper(9, 3);
  }
}
