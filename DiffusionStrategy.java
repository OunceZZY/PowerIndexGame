/**
 * Index    Strategy type
 * 1        GridLike Strategy
 * 
 * @author OwenZhang
 *
 */
public abstract class DiffusionStrategy {
  public static final char COL = 'C';
  public static final char DEF = 'D';

  double positive_power_number;

  public DiffusionStrategy() {}
  public DiffusionStrategy(double ppn) {
    positive_power_number = ppn;
  }
  public abstract void powerIndexTheBoard(Grid grid);
  public abstract Grid updateGrid(Grid grid);
}
