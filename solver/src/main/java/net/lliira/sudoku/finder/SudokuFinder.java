package net.lliira.sudoku.finder;

import net.lliira.sudoku.common.Sudoku;

import java.util.ArrayList;
import java.util.List;

public interface SudokuFinder {

  List<Sudoku> find(Sudoku sudoku);

  List<Sudoku> find(Sudoku sudoku, int limit);
}
