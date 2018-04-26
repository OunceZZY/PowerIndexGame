/**
 * Index    Strategy type
 * 1        GridLike Strategy
 * 
 * @author OwenZhang
 *
 */
public interface DiffusionStrategy {
  public static final char COL = 'C';
  public static final char DEF = 'D';
  public void initialTheBoard(Grid grid);
  public Grid updateGrid(Grid grid);
}
