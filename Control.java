
public class Control implements GameInitType {
  GameWindow GW;
  InfoMenu IM;
  DiffusionStrategy DS;

  GridLinkedList gll;

  int Height, Width;

  public Control() {
    IM = new InfoMenu(this);
  }

  void initialTheWindow(int ds_num, int grid_initialization, double powerX, int height,
      int width) {

    Height = height;
    Width = width;

    GW = new GameWindow(Height, Width);
    GW.ct = this;

    Grid curr = new Grid(Height, Width);
    gll = new GridLinkedList();
    gll.add(curr);

    // set strategy
    switch (ds_num) {
    case 1:
      DS = new GridLikeStrategy(powerX);
      break;
    }

    // set inital board looks like
    switch (grid_initialization) {
    case 1:
      HalfHalfAndOne(curr);
      break;
    }
  }

  /**
   * 
   * This method takes, where there should be one, the head of GridLinkedList
   * And calculate the power index of each vertex
   * 
   * Then put the UI board with the corresponding number presentation;
   */
  void initialTheGame() {
    if (gll.getSize() > 1) {
      Grid newHead = gll.cursor;
      gll.clear();
      gll.add(newHead);
    }
    gll.testprint();
    DS.powerIndexTheBoard(gll.head);
    GW.setTheBoard(null, gll.head);
  }

  void toNextStep() {
    if (gll.cursor.next == null) {
      gll.add(DS.updateGrid(gll.cursor));
    }
    gll.cursorToNext();
    GW.setTheBoard(gll.cursor.prev, gll.cursor);
  }

  void lastStep() {
    if (gll.cursor.prev == null) { return; }
    gll.cursorToPrev();
    GW.setTheBoard(gll.cursor.next, gll.cursor);
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
  }

  /**
   * to clear everything in the memory
   */
  void reinit() {
    System.out.println("reinit start");
    IM.setVisible(true);
    gll.clear();
    //GW.dispose();
    GW.setVisible(false);
    System.out.println("reinit done");
  }

}
