import java.text.DecimalFormat;

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

  public Vertex(char s, double p) {
    side = s;
    powerIndex = p;
  }
  
  public double getPowerIndex(){
    return powerIndex;
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

  public Vertex copy() {
    return new Vertex(side, powerIndex);
  }

  /**
   * Personally, I decided to make the toString method into form of
   * side+power Index (i.e., "C 0.3","D 0.2"
   * 
   * @return it returns the property of the vertex
   */
  @Override
  public String toString() {
    DecimalFormat formatter = new DecimalFormat("#0.000");
    return "" + side + " " + formatter.format(powerIndex);
  }

}
