package net.lliira.sudoku.finder;

import net.lliira.sudoku.storage.SudokuStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SudokuGenerator {
  private static final int TASKS = 4;
  private static final int SLEEP_INTERVAL_SEC = 6;

  private static final Logger logger = LogManager.getLogger(SudokuGenerator.class.getSimpleName());

  public static void main(String[] args) throws IOException, InterruptedException {
    SudokuStorage storage = new SudokuStorage(new File("./"));
    SudokuFinder finder = new DancingLinksFinder();
    SudokuGenerator generator = new SudokuGenerator(storage, finder);
    generator.generate();
  }

  private final SudokuStorage storage;
  private final SudokuFinder finder;
  private final ExecutorService executorService;
  private boolean running;

  public SudokuGenerator(SudokuStorage storage, SudokuFinder finder) {
    this.storage = storage;
    this.finder = finder;
    this.executorService = Executors.newFixedThreadPool(TASKS);
  }

  public void stop() {
    running = false;
  }

  public void generate() throws InterruptedException {
    logger.info("Starting Generator");
    AtomicInteger taskCounter = new AtomicInteger(0);
    running = true;
    int count = 0;
    while (running) {
      while (taskCounter.get() <= TASKS)
        executorService.submit(new GeneratorTask(storage, finder, taskCounter));
      if (++count % 10 == 0) logger.info("Running tasks: " + taskCounter.get());
      try {
        Thread.sleep(TimeUnit.SECONDS.toMillis(SLEEP_INTERVAL_SEC));
      } catch (InterruptedException e) {
        // early woke up, resume.
      }
    }
    executorService.shutdown();
    executorService.awaitTermination(1, TimeUnit.HOURS);
  }
}
