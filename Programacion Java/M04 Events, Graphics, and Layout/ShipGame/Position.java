/* This is a very general class. Thus, it should probably be placed in another
 * package along with GraphicsThread. However, for convenience we will keep it
 * with the rest for now.
 */
package shipgame;

import java.awt.geom.*;

/**A simple extension of Point2D.Double that allows for translation. Not sure
 * why Point allows for translation but Point2D does not. I thought about making
 * this class have the same interface as the Position class from EzWindows but
 * then thought better of it.
 *
 * @author Andrew Mertz
 */
public class Position extends Point2D.Double
{
  
  /**Adds <code>dx</code> to the x value of this point and <code>dy</code> to
   * the y value of this point.
   */
  public void translate(double dx, double dy)
  {
    x += dx;
    y += dy;
  }
  
  
  /**Adds <code>p.x</code> to the x value of this point and <code>p.y</code>
   * to the y value of this point.
   */
  public void translate(Point2D p)
  {
    translate(p.getX(), p.getY());
  }
  
  /** Creates a new instance of Position at the origin.
   */
  public Position()
  {
    super();
  }
  
  
  /** Creates a new instance of Position at (x,y).
   */
  public Position(double x, double y)
  {
    super(x, y);
  }
  
  
  /** Creates a new instance of Position at (p.x, p.y).
   */
  public Position(Point2D p)
  {
    super(p.getX(), p.getY());
  }
  
  /**Returns the magnitude of this position as if it were a vector. In other
   * words it returns <tt>(x<sup>2</sup> + y<sup>2</sup>)<sup>&frac12;</sup>.
   */
  public double magnitude()
  {
    return Math.sqrt(x * x + y * y);
  }
}
