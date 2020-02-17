package net.lliira.sudoku.storage;

import net.lliira.sudoku.common.Sudoku;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SudokuStorage {
  private static final String STORAGE_FILE = "sudoku.txt";

  private final Set<Sudoku> baseSudokus;
  private final Map<String, Sudoku> sudokuIndices;
  private final File storageFile;

  public SudokuStorage(File baseDirectory) throws IOException {
    baseSudokus = new HashSet<>();
    sudokuIndices = new HashMap<>();
    storageFile = new File(baseDirectory, STORAGE_FILE);
    loadFromDisk();
  }

  public Sudoku[] get() {
    return baseSudokus.toArray(new Sudoku[0]);
  }

  public boolean addSudoku(Sudoku sudoku) {
    if (isDuplicate(sudoku)) return false;

    expandAndAdd(sudoku);
    try {
      saveToFile();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return true;
  }

  private void loadFromDisk() throws IOException {
    if (!storageFile.exists()) return;
    try (var reader = new BufferedReader(new FileReader(storageFile))) {
      String line;
      while((line = reader.readLine()) != null) {
        line = line.trim();
        if(line.isEmpty())continue;
        Sudoku sudoku = new Sudoku(line);
        expandAndAdd(sudoku);
      }
    }
  }

  private void saveToFile() throws IOException {
    try (var writer = new PrintWriter(new FileWriter(storageFile))) {
      for (var sudoku : baseSudokus) {
        writer.println(sudoku.toString());
      }
      writer.flush();
    }
  }

  private boolean isDuplicate(Sudoku sudoku) {
    if (sudokuIndices.containsKey(sudoku.toString())) return true;

    // TODO: check supersets

    return false;
  }

  private void expandAndAdd(Sudoku sudoku) {
    var baseSudoku = sudoku.copy();
    baseSudokus.add(baseSudoku);

    sudokuIndices.put(sudoku.toString(), baseSudoku);
    for (int i =0; i <7; i++) {
      if (i == 3) sudoku.flip();
      else sudoku.rotate();
      sudokuIndices.put(sudoku.toString(), baseSudoku);
    }
  }

}
