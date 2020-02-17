package net.lliira.sudoku.finder;

import net.lliira.sudoku.common.Sudoku;
import net.lliira.sudoku.common.dlx.DancingLinks;
import net.lliira.sudoku.common.dlx.DancingMatrixHelper;

import java.util.LinkedList;
import java.util.List;

public class DancingLinksFinder implements SudokuFinder {

  private final DancingMatrixHelper matrixHelper;

  public DancingLinksFinder() {
    matrixHelper = new DancingMatrixHelper(Sudoku.SIZE, Sudoku.BOX);
  }

  @Override
  public  List<Sudoku> find(Sudoku sudoku) {
    return find(sudoku, DancingLinks.NO_LIMIT);
  }

  @Override
  public List<Sudoku> find(Sudoku sudoku, int limit) {
    int[][] cover = matrixHelper.convertInCoverMatrix(sudoku.get());
    DancingLinks dlx = new DancingLinks(cover, limit);
    dlx.process(0);
    List<Sudoku> solutions = new LinkedList<>();
    for (var result : dlx.results) {
      Sudoku solution = new Sudoku(matrixHelper.convertDLXListToGrid(result));
      solutions.add(solution);
    }
    return solutions;
  }
}
