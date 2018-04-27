import java.util.ArrayList;

public class GridLikeStrategy extends DiffusionStrategy {

  final int[][] closedNeighbor = { { 0, 0 }, { 0, -1 }, { 0, 1 }, { 1, 0 }, { -1, 0 } };
  final int[][] closedNeighborForTop = { { 0, 0 }, { 0, -1 }, { 0, 1 }, { 1, 0 } };
  final int[][] closedNeighborForBottom = { { 0, 0 }, { 0, -1 }, { 0, 1 }, { -1, 0 } };

  public GridLikeStrategy() {
  }

  @Override
  public void initialTheBoard(Grid grid) {
    int height = grid.row, width = grid.col;

    Vertex[][] graph = grid.getVertices();
    for (int i = 0; i < height; i++) { // identify the row
      for (int j = 0; j < width; j++) { // identify the column
        graph[i][j].powerIndex = this.calculatePowerIndex(graph, height, width, i, j,
            grid.positive_power_number);
      }
    }
  }

  @Override
  public Grid updateGrid(Grid grid) {
    int height = grid.row, width = grid.col;
    Vertex[][] previous = grid.getVertices();

    Grid nG = new Grid(grid.row, grid.col); // new Grid
    nG.positive_power_number = grid.positive_power_number;
    for (int i = 0; i < grid.row; i++) {
      for (int j = 0; j < grid.col; j++) {

        // first determin if change side
        boolean isOverriding = this.determineSide(previous, height, width, i, j,
            grid.positive_power_number);
        nG.getVertices()[i][j] = previous[i][j].copy();
        if (isOverriding)
          nG.getVertices()[i][j].changeSide();
      }
    }

    Vertex[][] graph = nG.getVertices();
    for (int i = 0; i < height; i++) { // identify the row
      for (int j = 0; j < width; j++) { // identify the column
        graph[i][j].powerIndex = this.calculatePowerIndex(graph, height, width, i, j,
            nG.positive_power_number);
      }
    }

    return nG;
  }

  boolean determineSide(Vertex[][] graph, int height, int width, int i, int j, double x) {
    int[][] specifiedDir;
    if (i == 0) specifiedDir = this.closedNeighborForTop;
    else if (i == height - 1) specifiedDir = this.closedNeighborForBottom;
    else specifiedDir = this.closedNeighbor;

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
    /*Vertex self = graph[i][j];
    Vertex thisNeigh;
    Vertex iterateNeigh;
    boolean isOverriding = true;
    for (int[] dir : specifiedDir) {
      thisNeigh = graph[i + dir[0]][((j + dir[1] + width) % width)];
      if (self.side != thisNeigh.side) {
        for (int[] dir2 : specifiedDir) {
          if (dir != dir2) {
            iterateNeigh = graph[i + dir2[0]][((j + dir2[1] + width) % width)];
            if (thisNeigh.side != iterateNeigh.side &&
                thisNeigh.getPowerIndex() <= iterateNeigh.getPowerIndex()) {
              isOverriding = false;
              break;
            }
          }
        }
        if (isOverriding) break;
        isOverriding = true;
      }
    }*/
    
  }

  double calculatePowerIndex(Vertex[][] graph, int height, int width, int i, int j,
      double x) {
    int cd[] = { 0, 0 };

    int[][] specifiedDir;
    if (i == 0) specifiedDir = this.closedNeighborForTop;
    else if (i == height - 1) specifiedDir = this.closedNeighborForBottom;
    else specifiedDir = this.closedNeighbor;

    for (int[] dir : specifiedDir) {
      cd[graph[i + dir[0]][((j + dir[1] + width) % width)].getSide() - super.COL]++;
    }
    // counting c and d done;
    double power = (double) cd[0] / (double) (cd[0] + cd[1]);;
    if (graph[i][j].side == Vertex.Collab)
      return (power < x) ? 0 : 1 / (double) cd[0];
    else
      return (power >= x) ? 0 : 1 / (double) cd[1];
  }

}
