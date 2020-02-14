package net.lliira.sudoku.solver;

public abstract class SudokuSolver {
    protected static final int SIZE = 9;
    protected static final int BOX = 3;
    protected static final int EMPTY = 0;

    protected int[][] board;

    public SudokuSolver(final int[][] board) {
        this.board = board;
    }

    public abstract boolean solve();

    public int[][] getBoard() {
        return board;
    }

    public void print() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                System.out.print(board[row][col]);
                System.out.print(", ");
            }
            System.out.println();
        }
    }

}
