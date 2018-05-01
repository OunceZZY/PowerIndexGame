
public class Control implements GameInitType {
  GameWindow GW;
  InfoMenu IM;
  DiffusionStrategy DS;

  GridLinkedList gll;
  private Grid alterPresent;

  int Height, Width;
  String cycleMessage = null;

  public void toCalcTheCycle() {
    initialTheGame();
    Grid fast = gll.head, slow = gll.head;
    int count = 0;
    int precycle = 0, cycleSize = 0;
    try {
      do {
        this.calculateNextState();
        this.calculateNextState();
        fast = fast.next.next;
        slow = slow.next;
        System.out.println("Is going" + count++);
      } while (!fast.equals(slow));
      slow = gll.head;
      while (!fast.equals(slow)) {
        this.calculateNextState();
        fast = fast.next;
        slow = slow.next;
        precycle++;
      }
      gll.precycleSize = precycle;
      gll.cycle = slow;
      do {
        slow = slow.next;
        cycleSize++;
      } while (!gll.cycle.equals(slow));
      gll.cycleSize = cycleSize - 1;
      cycleMessage = "Precycle: " + precycle + "Cycle: " + cycleSize;

      slow.prev.next = gll.cycle;
      gll.cleanAfter(slow);
      gll.cursor = gll.head;
    } catch (Exception e) {
      cycleMessage = "This graph probably don't have cycle\n" + e;
    }
    GW.setTheBoard(null, gll.head);
    GW.setCycleMessage(cycleMessage);
  }

  public Control() {
    IM = new InfoMenu(this);
    alterPresent = null;
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
    //gll.testprint();
    DS.powerIndexTheBoard(gll.head);
    GW.setTheBoard(null, gll.head);
  }

  /**
   * to reset the board to original position (from alterPresent
   */
  void setToNormal() {
    GW.setTheBoard(alterPresent, gll.cursor);
    alterPresent = null;
  }

  void calculateNextState() {
    gll.add(DS.updateGrid(gll.cursor));
    gll.cursorToNext();
  }

  void showNextState() {
    if (alterPresent != null) setToNormal();
    if (gll.cursor.next == null) {
      System.out.println("Creating new");
      calculateNextState();
      /*gll.add(DS.updateGrid(gll.cursor));*/
    }
    /*gll.cursorToNext();*/
    //gll.testprint();
    GW.setTheBoard(gll.cursor.prev, gll.cursor);
  }

  void showLastState() {
    if (alterPresent != null) setToNormal();
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
    // TODO change to cursor
    gll.head.getVertices()[row][col].changeSide();
  }

  /**
   * to clear everything in the memory
   */
  void reinit() {
    System.out.println("reinit start");
    IM.setVisible(true);
    gll.clear();
    GW.setVisible(false);
    System.out.println("reinit done");
  }

  /**
   * 
   * @param dir direction to shift -1 for left and 1 for right
   *          (see GameWindow)
   */
  void shiftTo(int dir) {
    if (alterPresent == null) alterPresent = gll.cursor;
    Grid shifted = shift(alterPresent, dir);
    GW.setTheBoard(alterPresent, shifted);
    alterPresent = shifted;
  }

  /**
   * separating the two methods since it suppose to load a new grid
   * 
   * @param v the original grid
   * @param shift direction as above
   * @return
   */
  Grid shift(Grid v, int shift) {
    Grid ret = new Grid(Height, Width);
    ret.grid = v.copyVertices();
    Vertex[][] orig = v.grid, then = ret.grid;
    for (int j = 0; j < Width; j++)
      for (int i = 0; i < Height; i++)
        then[i][(j + shift + Width) % Width] = orig[i][j];
    return ret;
  }

  void initialTheWindow(int ds_num, int grid_initialization,
      double powerX, int height, int width) {

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
    case 2:
      DS = new StrongGridStrategy(powerX);
      break;
    }

    // set inital board looks like
    switch (grid_initialization) {
    case 1:
      HalfHalfAndOne(curr);
      break;
    }
  }
}