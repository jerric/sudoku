package net.lliira.sudoku.finder;

import net.lliira.sudoku.common.dlx.DancingLinks;
import net.lliira.sudoku.common.dlx.DancingMatrixHelper;

import java.util.LinkedList;
import java.util.List;

public class DancingLinksFinder extends SudokuFinder {

  private final int limit;
  private final DancingMatrixHelper matrixHelper;

  public DancingLinksFinder(int[][] board, int limit) {
    super(board);
    this.limit = limit;
    matrixHelper = new DancingMatrixHelper(SIZE, BOX);
  }

  @Override
  public List<int[][]> find() {
    int[][] cover = matrixHelper.convertInCoverMatrix(board);
    DancingLinks dlx = new DancingLinks(cover, limit);
    dlx.process(0);
    List<int[][]> solutions = new LinkedList<>();
    for (var result : dlx.results) {
      solutions.add(matrixHelper.convertDLXListToGrid(result));
    }
    return solutions;
  }
}
