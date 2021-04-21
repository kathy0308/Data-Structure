package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/*
public class Percolation {
public Percolation(int N)
public void open(int row, int col)
public boolean isOpen(int row, int col)
public boolean isFull(int row, int col)
public int numberOfOpenSites()
public boolean percolates()
public static void main(String[] args)   
}
 */

public class Percolation {
    private boolean[][] grid;
    private int top;
    private int bottom;
    private int size;
    private int numberOfOpenSites = 0;
    private WeightedQuickUnionUF WQU;
    private WeightedQuickUnionUF UF;

    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        grid = new boolean[N][N];
        size = N;
        top = N * N;
        bottom = N * N + 1;

        WQU = new WeightedQuickUnionUF(N * N + 2);
        UF = new WeightedQuickUnionUF(N * N + 1);
    }

    private int xyTo1D(int row, int col) {
        return row * size + col;
    }

    private void validate(int row, int col) {
        if (row < 0 || col < 0 || row >= size || col >= size) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    public void open(int row, int col) {
        validate(row, col);

        if (!isOpen(row, col)) {
            grid[row][col] = true;
            numberOfOpenSites++;
        }
        int temp = xyTo1D(row, col);

        if (row == 0) {
            WQU.union(top, temp);
            UF.union(temp, top);
        }

        if (row == (size - 1)) {
            WQU.union(temp, bottom);
        }

        if (row > 0 && isOpen(row - 1, col)) {
            WQU.union(temp, xyTo1D(row - 1, col));
            UF.union(temp, xyTo1D(row - 1, col));
        }

        if (row < size - 1 && isOpen(row + 1, col)) {
            WQU.union(temp, xyTo1D(row + 1, col));
            UF.union(temp, xyTo1D(row + 1, col));
        }

        if (col > 0 && isOpen(row, col - 1)) {
            WQU.union(temp, xyTo1D(row, col - 1));
            UF.union(temp, xyTo1D(row, col - 1));
        }

        if (col < size - 1 && isOpen(row, col + 1)) {
            WQU.union(temp, xyTo1D(row, col + 1));
            UF.union(temp, xyTo1D(row, col + 1));
        }

    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row][col];
    }
    public boolean isFull(int row, int col) {
        validate(row, col);
        int temp = xyTo1D(row, col);
        return (isOpen(row, col) && UF.connected(top, temp));
    }
    public int numberOfOpenSites() {
        return numberOfOpenSites;

    }
    public boolean percolates() {
        return WQU.connected(top, bottom);

    }
    public static void main(String[] args) {

    }

}
