package net.lliira.sudoku.storage;

import net.lliira.sudoku.common.Sudoku;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SudokuStorageTest {

  private static final int[][] INPUT =
      new int[][] {
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

  @Test
  public void testSaveAndLoad() throws IOException {
    File testDirectory = Files.createTempDirectory("sudoku-test").toFile();
    SudokuStorage storage = new SudokuStorage(testDirectory);
    Assert.assertTrue(storage.addSudoku(new Sudoku(INPUT)));

    Sudoku[] sudokus = storage.get();
    Assert.assertEquals(1, sudokus.length);
    Assert.assertEquals(new Sudoku(INPUT), sudokus[0]);

    // now test loading by recreating the storage;
    storage = new SudokuStorage(testDirectory);
    sudokus = storage.get();
    Assert.assertEquals(1, sudokus.length);
    Assert.assertEquals(new Sudoku(INPUT), sudokus[0]);
  }

  @Test
  public void testAddDuplicate() throws IOException {
      File testDirectory = Files.createTempDirectory("sudoku-test").toFile();
      SudokuStorage storage = new SudokuStorage(testDirectory);
      Assert.assertTrue(storage.addSudoku(new Sudoku(INPUT)));
      Assert.assertFalse(storage.addSudoku(new Sudoku(INPUT)));
  }
}
