
public class Vertex {
  public static final char Collab = 'C';
  public static final char Defect = 'D';
  char side; // C for collaborator D for defector
  double powerIndex; // double [0,1]

  // constructor default: set to collaborator
  public Vertex() {
    side = 'C';
  }

  public Vertex(char s) {
    side = s;
  }

  public void changeSide() {
    side = (side == 'C') ? 'D' : 'C';
  }

  // notice this method should only be used when initialization
  public void setSide(char c) {
    side = c;
  }

  public char getSide() {
    return side;
  }

  // TODO calculate the power index
  public void calculatePowerIndex() {

  }

}
