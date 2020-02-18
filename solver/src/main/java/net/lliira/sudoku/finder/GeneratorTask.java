package net.lliira.sudoku.finder;

import net.lliira.sudoku.common.Sudoku;
import net.lliira.sudoku.common.math.Combinator;
import net.lliira.sudoku.storage.SudokuStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class GeneratorTask implements Runnable {
  private static final int MIN_DEGREE = 17;

  private static final Logger logger = LogManager.getLogger(GeneratorTask.class.getSimpleName());

  private final SudokuStorage storage;
  private final SudokuFinder finder;
  private final AtomicInteger runningCounter;
  private final Random random;

  public GeneratorTask(SudokuStorage storage, SudokuFinder finder, AtomicInteger taskCounter) {
    this.storage = storage;
    this.finder = finder;
    this.runningCounter = taskCounter;
    this.random = new Random();
    taskCounter.incrementAndGet();
  }

  @Override
  public void run() {
    logger.info("New Generator Task");
    Sudoku sudoku = initialize();
    var solutions = finder.find(sudoku, 2);
    try {
      if (!solutions.isEmpty()) {
        if (solutions.size() > 1) {
          sudoku = reduce(solutions.get(0));
        }
        if (storage.addSudoku(sudoku)) {
          logger.info("Found sudoku #{}, degree: {}", storage.size(), sudoku.degree());
          // sudoku.print();
        } else {
          logger.info("Duplicate sudoku, degree: {}", sudoku.degree());
        }
      }
    } finally {
      runningCounter.decrementAndGet();
      logger.info("Generator Task finished");
    }
  }

  private Sudoku initialize() {
    Sudoku sudoku = new Sudoku(new int[Sudoku.SIZE][Sudoku.SIZE]);
    int degree = 0;
    while (degree < MIN_DEGREE) {
      int row = random.nextInt(Sudoku.SIZE);
      int col = random.nextInt(Sudoku.SIZE);
      if (!sudoku.isEmpty(row, col)) continue;
      int number = random.nextInt(Sudoku.SIZE) + 1;
      if (sudoku.set(row, col, number)) degree++;
    }
    return sudoku;
  }

  private Sudoku reduce(Sudoku solution) {
    Sudoku sudoku = reduce1(solution);
    // logger.info("Level 1 Reduce: {}", sudoku.degree());
    sudoku = reduce2(sudoku, solution, 2);
    // logger.info("Level 2 Reduce: {}", sudoku.degree());
    sudoku = reduce2(sudoku, solution, 3);
    // logger.info("Level 3 Reduce: {}", sudoku.degree());

    return sudoku;
  }

  /**
   * repeatedly remove a number, until there are multiple solutions exists, return the last
   * 1-solution sudoku
   */
  private Sudoku reduce1(Sudoku solution) {
    List<Cell> cells = getCells(solution, false);
    // repeatedly remove a number, until there are multiple solutions exists.
    Sudoku previous = solution;
    while (!cells.isEmpty()) {
      Cell cell = cells.remove(random.nextInt(cells.size()));
      Sudoku sudoku = previous.copy();
      sudoku.unset(cell.row, cell.col);
      List<Sudoku> solutions = finder.find(sudoku, 2);

      if (solutions.size() == 1) previous = sudoku;
    }
    return previous;
  }

  /** randomly substitute 3 non-empty cells with 2 new ones */
  private Sudoku reduce2(Sudoku sudoku, Sudoku solution, int step) {
    assert step >= 2;
    Sudoku previous = sudoku;
    boolean reduced;
    do {
      reduced = false;
      sudoku = previous.copy();
      List<List<Cell>> removes = Combinator.combinations(getCells(sudoku, false), step);
      Collections.shuffle(removes);
      List<List<Cell>> adds = Combinator.combinations(getCells(sudoku, true), step - 1);
      Collections.shuffle(adds);
//      double total = removes.size() * adds.size();
//      long rounds = 0;
      for (List<Cell> remove : removes) {
        for (Cell cell : remove) sudoku.unset(cell.row, cell.col);
        for (List<Cell> add : adds) {
//          if (++rounds % 100_000 == 0)
//            logger.info(
//                "Progress: {}/{} ({}%)", rounds, total, Math.round(rounds / total * 1000) / 10.0);
          for (Cell cell : add) sudoku.set(cell.row, cell.col, solution.get(cell.row, cell.col));
          if (finder.find(sudoku, 2).size() == 1) {
//            logger.info("Reduced: {}->{}, {} rounds\n", previous.degree(), sudoku.degree(), rounds);
            previous = sudoku;
            reduced = true;
            break;
          }
          for (Cell cell : add) sudoku.unset(cell.row, cell.col);
        }
        if (reduced) break;
        for (Cell cell : remove) sudoku.set(cell.row, cell.col, solution.get(cell.row, cell.col));
      }
    } while (reduced);
    return previous;
  }

  private List<Cell> getCells(Sudoku sudoku, boolean empty) {
    List<Cell> cells = new ArrayList<>(Sudoku.SIZE * Sudoku.SIZE);
    for (int row = 0; row < Sudoku.SIZE; row++) {
      for (int col = 0; col < Sudoku.SIZE; col++) {
        if ((empty && sudoku.isEmpty(row, col)) || (!empty && !sudoku.isEmpty(row, col)))
          cells.add(new Cell(row, col));
      }
    }
    return cells;
  }
}
