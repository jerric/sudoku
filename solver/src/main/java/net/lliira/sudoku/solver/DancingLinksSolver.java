package net.lliira.sudoku.solver;

import net.lliira.sudoku.common.dlx.DancingLinks;
import net.lliira.sudoku.common.dlx.DancingMatrixHelper;

public class DancingLinksSolver extends SudokuSolver {

  private DancingMatrixHelper matrixHelper;

  public DancingLinksSolver(int[][] board) {
    super(board);
    matrixHelper = new DancingMatrixHelper(SIZE, BOX);
  }

  @Override
  public boolean solve() {
    int[][] cover = matrixHelper.convertInCoverMatrix(board);
    DancingLinks dlx = new DancingLinks(cover);
    dlx.process(0);
    if (dlx.results.isEmpty()) {
      return false;
    } else {
      board = matrixHelper.convertDLXListToGrid(dlx.results.get(0));
      return true;
    }
  }
}
