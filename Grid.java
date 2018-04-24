public class Grid {
  Grid prev, next;
  Vertex[][] grid;
  int row, col;
  

  public Grid(int row, int col) {
    this.row = row;
    this.col = col;
    for (int i = 0; i < row; i++)
      for (int j = 0; j < col; j++)
        grid[i][j] = new Vertex();
  }

  public Vertex[][] getGrid() {
    return grid;
  }

  public void updateGrid(int _row, int _col) {
    grid[_row][_col].changeSide();
  }

}