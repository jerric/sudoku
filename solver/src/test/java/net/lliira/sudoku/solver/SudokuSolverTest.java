package net.lliira.sudoku.solver;

import org.junit.Assert;
import org.junit.Test;

public class SudokuSolverTest {
  private static final int LOOPS = 1_000;

  private static int[][] input() {
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


  private static int[][] badInput() {
    return new int[][] {
        {8, 0, 0, 0, 0, 0, 0, 5, 0},
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


  private static int[][] solution() {
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
  public void testSolveBrutalForceRecursively() {
    var board = input();
    var solver = new BrutalForceSolver(board, true);
    Assert.assertTrue(solver.solve());
    Assert.assertArrayEquals(solution(), board);

    // nearly solved puzzle
    board = solution();
    board[0][0] = 0;
    solver = new BrutalForceSolver(board, true);
    Assert.assertTrue(solver.solve());
    Assert.assertArrayEquals(solution(), board);

    board = solution();
    board[8][8] = 0;
    solver = new BrutalForceSolver(board, true);
    Assert.assertTrue(solver.solve());
    Assert.assertArrayEquals(solution(), board);

    // fully solved puzzle
    board = solution();
    solver = new BrutalForceSolver(board, true);
    Assert.assertTrue(solver.solve());
    Assert.assertArrayEquals(solution(), board);

    // bad input, no solution
    solver = new BrutalForceSolver(badInput(), true);
    Assert.assertFalse(solver.solve());
  }

  @Test
  public void testSolveBrutalForceLinearly() {
    var board = input();
    var solver = new BrutalForceSolver(board, false);
    Assert.assertTrue(solver.solve());
    Assert.assertArrayEquals(solution(), board);

    // nearly solved puzzle
    board = solution();
    board[0][0] = 0;
    solver = new BrutalForceSolver(board, false);
    Assert.assertTrue(solver.solve());
    Assert.assertArrayEquals(solution(), solver.getBoard());

    board = solution();
    board[8][8] = 0;
    solver = new BrutalForceSolver(board, false);
    Assert.assertTrue(solver.solve());
    Assert.assertArrayEquals(solution(), solver.getBoard());

    // fully solved puzzle
    board = solution();
    solver = new BrutalForceSolver(board, false);
    Assert.assertTrue(solver.solve());
    Assert.assertArrayEquals(solution(), solver.getBoard());

    // bad input, no solution
    solver = new BrutalForceSolver(badInput(), false);
    Assert.assertFalse(solver.solve());
  }

  @Test
  public void testSolveDancingLinks() {
    var board = input();
    var solver = new DancingLinksSolver(board);
    Assert.assertTrue(solver.solve());
    Assert.assertArrayEquals(solution(), solver.getBoard());

    // nearly solved puzzle
    board = solution();
    board[0][0] = 0;
    solver = new DancingLinksSolver(board);
    Assert.assertTrue(solver.solve());
    Assert.assertArrayEquals(solution(), solver.getBoard());

    board = solution();
    board[8][8] = 0;
    solver = new DancingLinksSolver(board);
    Assert.assertTrue(solver.solve());
    Assert.assertArrayEquals(solution(), solver.getBoard());

    // fully solved puzzle
    board = solution();
    solver = new DancingLinksSolver(board);
    Assert.assertTrue(solver.solve());
    Assert.assertArrayEquals(solution(), solver.getBoard());

    // bad input, no solution
    solver = new DancingLinksSolver(badInput());
    Assert.assertFalse(solver.solve());
}

    @Test
  public void timedTests() {
    long start = System.currentTimeMillis();
    for (int i = 0; i < LOOPS; i++) {
      var board = input();
      var solver = new BrutalForceSolver(board, true);
      Assert.assertTrue(solver.solve());
    }
    System.out.printf("Recursive Time: %.3f sec\n", (System.currentTimeMillis() - start) / 1000.0);

    start = System.currentTimeMillis();
    for (int i = 0; i < LOOPS; i++) {
     var board = input();
      var solver = new BrutalForceSolver(board, false);
      Assert.assertTrue(solver.solve());
    }
    System.out.printf("Linear Time: %.3f sec\n", (System.currentTimeMillis() - start) / 1000.0);

      start = System.currentTimeMillis();
      for (int i = 0; i < LOOPS; i++) {
        var board = input();
        var solver = new DancingLinksSolver(board);
        Assert.assertTrue(solver.solve());
      }
      System.out.printf("DLX Time: %.3f sec\n", (System.currentTimeMillis() - start) / 1000.0);
    }
}
