
public class Grid {
  Grid prev, next;
  Vertex[][] grid;
  //double positive_power_number;
  int row, col;

  public Grid(int row, int col) {
    prev = null;
    next = null;
    this.row = row;
    this.col = col;

    grid = new Vertex[row][col];

    for (int i = 0; i < row; i++)
      for (int j = 0; j < col; j++)
        grid[i][j] = new Vertex();

  }

  public Vertex[][] getVertices() {
    return grid;
  }

  public Vertex[][] copyVertices() {
    Vertex[][] toCp = new Vertex[row][col];
    for (int i = 0; i < row; i++)
      for (int j = 0; j < col; j++)
        toCp[i][j] = this.grid[i][j].copy();
    return toCp;
  }

  public void alterVertexSide(int _row, int _col) {
    grid[_row][_col].changeSide();
  }

  public void setVertexSide(int _row, int _col, char side) {
    grid[_row][_col].side = side;
  }

  @Override
  public String toString() {
    String ret = "";
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++)
        ret += grid[i][j].toString() + '\t';
      ret += '\n';
    }
    return ret;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Grid)) return false;
    Grid obj = (Grid) o;
    if (obj.col != this.col || obj.row != this.row) return false;
    Vertex[][] objs = obj.getVertices();
    for (int i = 0; i < row; i++)
      for (int j = 0; j < col; j++)
        if (!objs[i][j].onTheSameSideWith(grid[i][j])) { return false; }
    return true;
  }
}