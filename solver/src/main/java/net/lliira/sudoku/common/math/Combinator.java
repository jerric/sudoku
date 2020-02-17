package net.lliira.sudoku.common.math;

import java.util.ArrayList;
import java.util.List;

public class Combinator {

  public static <T> List<List<T>> combinations(List<T> data, int picks) {
    List<T> holder = new ArrayList<>(picks);
    // initialize holder with random data
    for (int i = 0; i < picks; i++){
      holder.add(null);
    }
    List<List<T>> result = new ArrayList<>();
    findCombination(data, result, picks, 0, holder, 0);
    return result;
  }

  private static <T> void findCombination(
      List<T> data, List<List<T>> results, int picks, int index, List<T> holder, int i) {
    // Current combination is ready to be printed, print it
    if (index == picks) {
      results.add(new ArrayList<>(holder));
      return;
    }

    // When no more elements are there to put in data[]
    if (i >= data.size()) return;

    // current is included, put next at next location
    holder.set(index, data.get(i));
    findCombination(data, results, picks, index + 1, holder, i + 1);

    // current is excluded, replace it with next (Note that
    // i+1 is passed, but index is not changed)
    findCombination(data, results, picks, index, holder, i + 1);
  }
}
