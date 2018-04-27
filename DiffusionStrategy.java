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
  public abstract void initialTheBoard(Grid grid);
  public abstract Grid updateGrid(Grid grid);
}
