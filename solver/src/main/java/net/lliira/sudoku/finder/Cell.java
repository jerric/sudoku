package net.lliira.sudoku.finder;

public class Cell {
  public final int row;
  public final int col;

  public Cell(int row, int col) {
    this.row = row;
    this.col = col;
  }

  @Override
  public int hashCode() {
    return row ^ col;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Cell) {
      Cell cell = (Cell) obj;
      return row == cell.row && col == cell.col;
    } else return false;
  }
}