package net.lliira.sudoku.common;

import org.junit.Assert;
import org.junit.Test;

public class SudokuTest {

  private static String PATTERN =
      "8;0036;0700902;050007;0000457;00010003;001000068;00850001;0900004";
  private static String FULL_PATTERN =
      "812753649;943682175;675491283;154237896;369845721;287169534;521974368;438526917;796318452";

  private static int[][] array() {
    return new int[][] {
      {8, 0, 0, 0, 0, 0, 0, 0, 0},
      {0, 0, 3, 6, 0, 0, 0, 0, 0},
      {0, 7, 0, 0, 9, 0, 2, 0, 0},
      {0, 5, 0, 0, 0, 7, 0, 0, 0},
      {0, 0, 0, 0, 4, 5, 7, 0, 0},
      {0, 0, 0, 1, 0, 0, 0, 3, 0},
      {0, 0, 1, 0, 0, 0, 0, 6, 8},
      {0, 0, 8, 5, 0, 0, 0, 1, 0},
      {0, 9, 0, 0, 0, 0, 4, 0, 0}
    };
  }

  private static int[][] fullArray() {
    return new int[][] {
      {8, 1, 2, 7, 5, 3, 6, 4, 9},
      {9, 4, 3, 6, 8, 2, 1, 7, 5},
      {6, 7, 5, 4, 9, 1, 2, 8, 3},
      {1, 5, 4, 2, 3, 7, 8, 9, 6},
      {3, 6, 9, 8, 4, 5, 7, 2, 1},
      {2, 8, 7, 1, 6, 9, 5, 3, 4},
      {5, 2, 1, 9, 7, 4, 3, 6, 8},
      {4, 3, 8, 5, 2, 6, 9, 1, 7},
      {7, 9, 6, 3, 1, 8, 4, 5, 2}
    };
  }

  @Test
  public void testCreateByArray() {
    Sudoku sudoku = new Sudoku(array());
    Assert.assertArrayEquals(array(), sudoku.get());
    Assert.assertEquals(PATTERN, sudoku.toString());

    sudoku = new Sudoku(fullArray());
    Assert.assertArrayEquals(fullArray(), sudoku.get());
    Assert.assertEquals(FULL_PATTERN, sudoku.toString());
  }

  @Test
  public void testCreateByPattern() {
    Sudoku sudoku = new Sudoku(PATTERN);
    Assert.assertArrayEquals(array(), sudoku.get());
    Assert.assertEquals(PATTERN, sudoku.toString());

    sudoku = new Sudoku(FULL_PATTERN);
    Assert.assertArrayEquals(fullArray(), sudoku.get());
    Assert.assertEquals(FULL_PATTERN, sudoku.toString());
  }

  @Test
  public void testFlip() {
    int[][] expected =
        new int[][] {
          {9, 4, 6, 3, 5, 7, 2, 1, 8},
          {5, 7, 1, 2, 8, 6, 3, 4, 9},
          {3, 8, 2, 1, 9, 4, 5, 7, 6},
          {6, 9, 8, 7, 3, 2, 4, 5, 1},
          {1, 2, 7, 5, 4, 8, 9, 6, 3},
          {4, 3, 5, 9, 6, 1, 7, 8, 2},
          {8, 6, 3, 4, 7, 9, 1, 2, 5},
          {7, 1, 9, 6, 2, 5, 8, 3, 4},
          {2, 5, 4, 8, 1, 3, 6, 9, 7},
        };
    Sudoku sudoku = new Sudoku(fullArray());
    sudoku.flip();
    Assert.assertArrayEquals(expected, sudoku.get());
  }

  @Test
  public void testRotate() {
    int[][] expected =
        new int[][] {
            {9, 5, 3, 6, 1, 4, 8, 7, 2},
            {4, 7, 8, 9, 2, 3, 6, 1, 5},
            {6, 1, 2, 8, 7, 5, 3, 9, 4},
            {3, 2, 1, 7, 5, 9, 4, 6, 8},
            {5, 8, 9, 3, 4, 6, 7, 2, 1},
            {7, 6, 4, 2, 8, 1, 9, 5, 3},
            {2, 3, 5, 4, 9, 7, 1, 8, 6},
            {1, 4, 7, 5, 6, 8, 2, 3, 9},
            {8, 9, 6, 1, 3, 2, 5, 4, 7},
        };
    Sudoku sudoku = new Sudoku(fullArray());
    sudoku.rotate();
    Assert.assertArrayEquals(expected, sudoku.get());
  }
}
