package net.lliira.sudoku.common.dlx;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DancingLinks {
    public static final int NO_LIMIT = 0;

    private int limit;
    private ColumnNode header;
    private LinkedList<DancingNode> answer;
    public List<List<DancingNode>> results;

    public DancingLinks(int[][] cover) {
      this(cover, NO_LIMIT);
    }

    public DancingLinks(int[][] cover, int limit) {
      this.limit = limit;
      answer = new LinkedList<>();
      results = new LinkedList<>();
      header = createDLXList(cover);
    }

    private ColumnNode createDLXList(int[][] grid) {
      final int nbColumns = grid[0].length;
      ColumnNode headerNode = new ColumnNode("header");
      List<ColumnNode> columnNodes = new ArrayList<>();

      for (int i = 0; i < nbColumns; i++) {
        ColumnNode n = new ColumnNode(i + "");
        columnNodes.add(n);
        headerNode = (ColumnNode) headerNode.linkRight(n);
      }

      headerNode = headerNode.right.column;

      for (int[] aGrid : grid) {
        DancingNode prev = null;

        for (int j = 0; j < nbColumns; j++) {
          if (aGrid[j] == 1) {
            ColumnNode col = columnNodes.get(j);
            DancingNode newNode = new DancingNode(col);

            if (prev == null) prev = newNode;

            col.top.linkDown(newNode);
            prev = prev.linkRight(newNode);
            col.size++;
          }
        }
      }
      headerNode.size = nbColumns;
      return headerNode;
    }

  /**
   * Returns true if the process scanned all solutions; false if it exits early due to limit.
   */
    public boolean process(int k) {
      if (header.right == header) {
        // End of Algorithm X
        // Result is copied in a result list
        results.add(new LinkedList<>(answer));
        if (limit > NO_LIMIT && results.size() >= limit) return false;
      } else {
        // we choose column c
        ColumnNode c = selectColumnNodeHeuristic();
        c.cover();

        for (DancingNode r = c.bottom; r != c; r = r.bottom) {
          // We add r line to partial solution
          answer.addLast(r);

          // We cover columns
          for (DancingNode j = r.right; j != r; j = j.right) {
            j.column.cover();
          }

          // recursive call to level k + 1
          if (!process(k + 1)) return false;

          // We go back
          r = answer.removeLast();
          c = r.column;

          // We uncover columns
          for (DancingNode j = r.left; j != r; j = j.left) {
            j.column.uncover();
          }
        }
        c.uncover();
      }
      return true;
    }

    private ColumnNode selectColumnNodeHeuristic() {
      int min = Integer.MAX_VALUE;
      ColumnNode ret = null;
      for (ColumnNode c = header.right.column; c != header; c = c.right.column) {
        if (c.size < min) {
          min = c.size;
          ret = c;
        }
      }
      return ret;
    }
  }

