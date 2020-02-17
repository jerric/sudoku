package net.lliira.sudoku.common.math;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class CombinatorTest {

  @Test
  public void testOnePick() {
    List<List<Integer>> results = Combinator.combinations(List.of(1, 2, 3), 1);
    assertThat(results, containsInAnyOrder(List.of(1), List.of(2), List.of(3)));
  }

  @Test
  public void testTwoPicks() {
    List<List<Integer>> results = Combinator.combinations(List.of(1, 2), 2);
    assertThat(results, containsInAnyOrder(List.of(1, 2)));

    results = Combinator.combinations(List.of(1, 2, 3), 2);
    assertThat(results, containsInAnyOrder(List.of(1, 2), List.of(1, 3), List.of(2, 3)));

    results = Combinator.combinations(List.of(1, 2, 3, 4, 5), 2);
    assertThat(
        results,
        containsInAnyOrder(
            List.of(1, 2),
            List.of(1, 3),
            List.of(1, 4),
            List.of(1, 5),
            List.of(2, 3),
            List.of(2, 4),
            List.of(2, 5),
            List.of(3, 4),
            List.of(3, 5),
            List.of(4, 5)));
  }

  @Test
  public void testThreePicks() {
    List<List<Integer>> results = Combinator.combinations(List.of(1, 2, 3), 3);
    assertThat(results, containsInAnyOrder(List.of(1, 2, 3)));

    results = Combinator.combinations(List.of(1, 2, 3, 4), 3);
    assertThat(
        results,
        containsInAnyOrder(List.of(1, 2, 3), List.of(1, 2, 4), List.of(1, 3, 4), List.of(2, 3, 4)));

    results = Combinator.combinations(List.of(1, 2, 3, 4, 5), 3);
    assertThat(
        results,
        containsInAnyOrder(
            List.of(1, 2, 3),
            List.of(1, 2, 4),
            List.of(1, 2, 5),
            List.of(1, 3, 4),
            List.of(1, 3, 5),
            List.of(1, 4, 5),
            List.of(2, 3, 4),
            List.of(2, 3, 5),
            List.of(2, 4, 5),
            List.of(3, 4, 5)));
  }
}
