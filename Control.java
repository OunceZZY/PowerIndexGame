
public class Control implements GameInitType {
  GameWindow GW;
  InfoMenu IM;
  DiffusionStrategy DS;

  GridLinkedList gll;

  int Height, Width;

  public Control() {
    IM = new InfoMenu(this);
  }

  /**
   * This method takes, where there should be one, the head of GridLinkedList
   * And calculate the power index of each vertex
   * 
   * Then put the UI board with the corresponding number presentation;
   */
  void initialTheGame() {
    DS.initialTheBoard(gll.head);
    GW.setTheBoard(gll.head);
  }

  void toNextStep() {
    Grid updatedGrid = DS.updateGrid(gll.cursor);
    gll.add(updatedGrid);
    gll.cursorToNext();

    for (int i = 0; i < Height; i++) {
      for (int j = 0; j < Width; j++) {
        GW.setButtonSide(i, j, gll.cursor.getVertices()[i][j].side);
        GW.setButtonText(i, j, "" + gll.cursor.getVertices()[i][j].getPowerIndex());
      }
    }
  }

  void initialization(int ds_num, int grid_initialization, double powerX, int height,
      int width) {

    gll = new GridLinkedList();
    Height = height;
    Width = width;

    GW = new GameWindow(Height, Width);
    GW.ct = this;

    Grid curr = new Grid(Height, Width);
    curr.positive_power_number = powerX;

    gll.add(curr);

    // TODO set strategy
    switch (ds_num) {
    case 1:
      DS = new GridLikeStrategy();
      break;
    }

    switch (grid_initialization) {
    case 1:
      HalfHalfAndOne(curr);
      break;
    }

  }

  public void HalfHalfAndOne(Grid curr) {
    int topHalf = Height / 2;
    for (int i = 0; i < topHalf; i++) {
      for (int j = 0; j < Width; j++) {
        curr.setVertexSide(i, j, Vertex.Defect);
        GW.setButtonSide(i, j, Vertex.Defect);
      }
    }
    for (int i = topHalf; i < Height; i++) {
      for (int j = 0; j < Width; j++) {
        curr.setVertexSide(i, j, Vertex.Collab);
        GW.setButtonSide(i, j, Vertex.Collab);
      }
    }
    curr.setVertexSide(topHalf - 1, Width / 2, Vertex.Collab);
    GW.setButtonSide(topHalf - 1, Width / 2, Vertex.Collab);
  }

  /**
   * this method should only be used when initializing the game
   * the user free to calculate change the grid looks like
   * 
   * @param row
   * @param col
   */
  void broadcastchangeSide(int row, int col) {
    GW.changeButtonSide(row, col);
    gll.head.getVertices()[row][col].changeSide();
    // TODO update the thing in grid's side;
  }

  void reinit() {
    System.out.println("reinit start");
    IM.setVisible(true);
    gll.clear();
    //GW.dispose();
    GW.setVisible(false);
    //GW = null;
    System.out.println("reinit done");
  }

}
