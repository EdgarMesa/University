/* This is a very general class and could be useful in many situations. Thus, it
 * should probably be placed in another package along with Position. However,
 * for convenience we will keep it with the rest for now. 
 */
package shipgame;

import java.awt.Component;

/**This class is a <code>Thread</code> that repeatedly calls the 
 * <code>repaint</code> method of a fixed <code>Component</code>. Repaints are 
 * called at intervals in an attempt to maintain a fixed frame rate. 
 *
 * @author Andrew Mertz
 */
public class GraphicsThread extends Thread
{
  /**A reference to the Component we are painting.
   */
  private Component c;
  
  /**The "time", according to <code>System.nanoTime()</code> when we will want 
   * to call repaint again.
   */
  private long nextPaintTime; 
  
  /**The difference between the current time, according to 
   * <code>System.nanoTime()</code>, and <code>nextPaintTime</code>.
   */
  private long  timeTillNextPaint = 0;
  
  /**The time in ms between repaints.
   */
  private long  sleeptime;
  
  /**Creates a new instance of <code>GraphicsThread</code>.
   *
   * @param c The Component this thread is to repaint.
   * @param sleeptime The time in ms between repaints.
   */
  public GraphicsThread(Component c, long sleeptime)
  {
    // Set the name of this thread to "Graphics Thread"
    super("Graphics Thread");
    
    // Initialize data members.
    this.c = c;
    this.sleeptime = sleeptime;
  }
  
  /**Start making repaint calls. 
   */
  public void run()
  {
    /* When we start we want to call repaint right away so we set the 
     * nextPaintTime to be the current time.
     */
    nextPaintTime = System.nanoTime();

    // Until this thread is killed we want to keep calling update.
    while(true)
    {
      // Get the current time.
      long currentTime = System.nanoTime();
      
      // Is the next time to paint in the future.
      if(nextPaintTime > currentTime)
          /* If so record how long it is till the next call to repaint should be
           * made. Note that time has ns resolution but sleep takes ms. Thus we
           * divide by 1,000,000 to convert between the two. 
           */
          timeTillNextPaint = (nextPaintTime - currentTime)/1000000;
        
      // Sleep till the next frame.
      try
      {
        Thread.sleep(timeTillNextPaint);
      }
      catch(InterruptedException e){} // It is odd to get such an exception.
      
      /* Note that time has ns resolution but sleep takes ms. Thus we multiply
       * by 1,000,000 to convert between the two.
       */
      nextPaintTime += sleeptime * 1000000;
      
      // Repaint c
      c.repaint();
    }
  }
}
