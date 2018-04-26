
public class GridLikeStrategy implements DiffusionStrategy {

  public GridLikeStrategy() {
  }

  @Override
  public void initialTheBoard(Grid grid) {
    // TODO Auto-generated method stub
    int height = grid.row;
    int width = grid.col;

    int[] cd = new int[2];//c = 0, d = 0;
    cd[0] = 0;
    cd[1] = 0;
    Vertex[][] graph = grid.getVertices();
    for (int i = 0; i < height; i++) { // identify the row
      for (int j = 0; j < width; j++) { // identify the column
        //TODO delete here print
        System.out.println("" + (i - 1) + " " + ((j + 1) % width));
        System.out.println("" + (i + 1) + " " + ((j - 1 + width) % width));
        // up down left right
        if (i - 1 >= 0) cd[graph[i - 1][j].getSide() - 'C']++;
        if (i + 1 < height) cd[graph[i + 1][j].getSide() - 'C']++;
        cd[graph[i][(j + 1) % width].getSide() - 'C']++;
        cd[graph[i][(j - 1 + width) % width].getSide() - 'C']++;
        /*for (int hori = -1; hori < 1; hori += 2) {
          for (int verti = -1; verti <= 1; verti += 2) {
            System.out.println("" + (i + verti) + " " + ((j + hori + width) % width));
            if (i + verti >= 0 && i + verti < width) {
              
              cd[graph[i + verti][((j + hori + width) % width)].getSide()]++;
            }
          }*/
        // checking c and d done;
        double power = (double) cd[0] / (double) (cd[0] + cd[1]);
        if (grid.grid[i][j].side == Vertex.Collab)
          grid.grid[i][j].powerIndex = (power < grid.positive_power_number) ? 0
              : 1 / (double) cd[0];
        else
          grid.grid[i][j].powerIndex = (power >= grid.positive_power_number) ? 0
              : 1 / (double) cd[1];
        cd[0] = 0;
        cd[1] = 0;
      }

    }
  }

  @Override
  public Grid updateGrid(Grid grid) {
    // TODO Auto-generated method stub
    return null;
  }

}
