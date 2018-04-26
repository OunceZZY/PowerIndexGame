public class Grid {
  Grid prev, next;
  Vertex[][] grid;
  double positive_power_number;
  int row, col;

  public Grid(int row, int col)  {
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

  public void alterVertexSide(int _row, int _col) {
    grid[_row][_col].changeSide();
  }
  
  public void setVertexSide(int _row,int _col,char side){
    grid[_row][_col].side = side;
  }
  
 

}