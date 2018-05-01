import java.util.ArrayList;

public class GridLikeStrategy extends DiffusionStrategy {

  final static int[][] closedNeighbor = { { 0, 0 }, { 0, -1 }, { 0, 1 }, { 1, 0 }, { -1, 0 } };
  final static int[][] closedNeighborForTop = { { 0, 0 }, { 0, -1 }, { 0, 1 }, { 1, 0 } };
  final static int[][] closedNeighborForBottom = { { 0, 0 }, { 0, -1 }, { 0, 1 }, { -1, 0 } };

  public GridLikeStrategy(double ppn) {
    super(ppn);
  }

  /**
   * 
   * @param grid the current grid
   * @param ppn the positive power number of the index
   * @return
   */
  @Override
  public Grid updateGrid(Grid grid) {
    int height = grid.row, width = grid.col;
    Grid nG = new Grid(grid.row, grid.col); // new Grid

    Vertex[][] previous = grid.getVertices();
    for (int i = 0; i < grid.row; i++) {
      for (int j = 0; j < grid.col; j++) {

        // first determine if change side
        boolean isOverriding = this.determineSide(previous, height, width, i, j);
        nG.getVertices()[i][j] = previous[i][j].copy();
        if (isOverriding)
          nG.getVertices()[i][j].changeSide();
      }
    }
    powerIndexTheBoard(nG);
    return nG;
  }

  /**
   * As explanation as determineSide and calculatePowerIndex
   * 
   * @param height
   * @param i
   * @param j
   * @return
   */
  int[][] chooseSpecifiedDirection(int height, int i, int j) {
    int[][] specifiedDir;
    if (i == 0) specifiedDir = closedNeighborForTop;
    else if (i == height - 1) specifiedDir = closedNeighborForBottom;
    else specifiedDir = closedNeighbor;
    return specifiedDir;
  }

  /**
   * giving a vertex [][] and determine the vertex on i,j
   * is it going to be overrode in the next iteration
   * 
   * @param graph the vertex grid
   * @param height number of rows
   * @param width number of columns
   * @param i the row to determine
   * @param j the column to determine
   * @return whether or not there is a overriding situation
   */
  boolean determineSide(Vertex[][] graph, int height, int width, int i, int j) {
    int[][] specifiedDir = this.chooseSpecifiedDirection(height, i, j);
    ArrayList<Double> onThisSide = new ArrayList<>();
    ArrayList<Double> onTheOtherSide = new ArrayList<>();
    Vertex self = graph[i][j], thisNeigh;
    for (int[] dir : specifiedDir) {
      thisNeigh = graph[i + dir[0]][((j + dir[1] + width) % width)];
      if (self.getSide() == thisNeigh.getSide())
        onThisSide.add(thisNeigh.getPowerIndex());
      else onTheOtherSide.add(thisNeigh.getPowerIndex());
    }
    boolean override = true;
    for (double wantToOverride : onTheOtherSide) {
      for (double wantToDefend : onThisSide) {
        if (wantToOverride <= wantToDefend) {
          override = false;
          break;
        }
      }
      if (override) return true;
      override = true;
    }
    return false;
  }

  double calculatePowerIndex(Vertex[][] graph, int height, int width, int i, int j) {
    int cd[] = { 0, 0 };
    int[][] specifiedDir = this.chooseSpecifiedDirection(height, i, j);
    for (int[] dir : specifiedDir)
      cd[graph[i + dir[0]][((j + dir[1] + width) % width)].getSide() - COL]++;
    // counting c and d done;
    double power = (double) cd[0] / (double) (cd[0] + cd[1]);;
    if (graph[i][j].side == Vertex.Collab)
      return (power < positive_power_number) ? 0 : 1 / (double) cd[0];
    else
      return (power >= positive_power_number) ? 0 : 1 / (double) cd[1];
  }

  @Override
  public void powerIndexTheBoard(Grid grid) {
    int height = grid.row, width = grid.col;
    Vertex[][] graph = grid.getVertices();
    for (int i = 0; i < height; i++) { // identify the row
      for (int j = 0; j < width; j++) { // identify the column
        graph[i][j].powerIndex = calculatePowerIndex(graph, height, width, i, j);
      }
    }
  }

}
