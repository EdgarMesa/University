/* All of the classes for the ship game will be placed into the package 
 * shipgame. It is a good idea not to place classes into the default package 
 * (i.e. always have a package declaration) to help avoid namescape collisions.
 */
package shipgame;

/* Bring the namespace of a few Java packages into view.
 */
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**This class encapsulates all of the information about a game. It extends
 * <code>JFrame</code> thus it also is a window that can display the current
 * state of the game.
 *
 * @author Andrew Mertz
 */
public class Game extends JFrame
{
  /**Holds the current status information for player 1.
   */
  private String player1String = "";
  /**Holds the current status information for player 2.
   */
  private String player2String = "";
  /**The number of times player 1 has won.
   */
  private int player1Score = 0;
  /**The number of times player 2 has won.
   */
  private int player2Score = 0;
  /**A hard coded constant for the force of the black hole. The magnitude of the
   * force an object feels is equal to 
   * <tt>G_FACTOR / (d / G_DROPOFF)<sup>2</sup></tt> where <tt>d</tt> is the
   * distance of the back hole to the object in pixels. Thus, the force is
   * inversely proportional to the square of the distance and
   * {@linkplain shipgame.Game#G_DROPOFF} is the conversion factor from pixels 
   * to units of distance.
   * 
   * @see shipgame.Game#gForce
   * @see shipgame.Game#G_DROPOFF
   */
  static final double G_FACTOR = 5.0;
  /**A hard coded constant for the rate at which the force of the black hole 
   * drops off. Thus, it is the conversion factor from pixels to units of
   * distance. It is also considered to be the size of the black hole for 
   * collision purposes. The magnitude of the force an object feels is equal to 
   * <tt>G_FACTOR / (d / G_DROPOFF)<sup>2</sup></tt> where <tt>d</tt> is the 
   * distance to the object in pixels. Thus, the force is inversely proportional
   * to the square of the converted distance. 
   * 
   * @see shipgame.Game#gForce
   * @see shipgame.Game#G_FACTOR
   */
  static final double G_DROPOFF = 25.0;

  /**Computes the gravitational force on a given <code>GameObject</code> in both
   * the <tt>x</tt> and <tt>y</tt> axis. The magnitude of the force an object 
   * feels is equal to <tt>G_FACTOR / (d / G_DROPOFF)<sup>2</sup></tt> where 
   * <tt>d</tt> is the distance to the object in pixels. Thus, the force is 
   * inversely proportional to the square of the distance and 
   * {@linkplain shipgame.Game#G_DROPOFF} is the conversion factor from pixels 
   * to units of distance.
   * 
   * @param o The object to compute the force on.
   * @return The force exerted on the given object decomposed into the x and y
   * axis.
   * @see shipgame.Game#G_FACTOR
   * @see shipgame.Game#G_DROPOFF
   */
  static Position gForce(GameObject o)
  {
    // Find the distance between the black hole and o.
    double d = o.location.distance(DEFAULT_WIDTH / 2.0, DEFAULT_HEIGHT / 2.0);

    // Find the magnitude of the gravitational force.
    double gFactor = G_FACTOR / Math.pow(d / G_DROPOFF, 2);

    /*           *
     *          **
     *         * *
     *      h *  *y
     *       *   *
     *      *    *
     *     *t    *
     *    ********
     *        x
     *
     *   x = h * cos(t)
     *   y = h * sin(t)
     */
    return new Position(gFactor * (DEFAULT_WIDTH / 2.0 - o.location.x) / d,
                        gFactor * (DEFAULT_HEIGHT / 2.0 - o.location.y) / d);
  }
  /**A hard coded constant that holds the default background color for the game.
   */
  static final Color BACKGROUND = Color.BLACK;
  /**Winner is zero is no one has won yet or it is the player number of the 
   * current winner. 
   */
  int winner = 0;
  /**A hard coded constant for the width of the playing surface in pixels.
   */
  static final int DEFAULT_WIDTH = 1200;
  /**A hard coded constant for the height of the playing surface in pixels.
   */
  static final int DEFAULT_HEIGHT = 900;
  /**Holds all of the currently active <code>GameObject</code>s in the game.
   */
  private Collection<GameObject> objectCollection;
  /**Holds all of the <code>GameObject</code>s that are to be removed after the 
   * current update.
   *
   * @see shipgame.Game#putInTheBook
   */
  private Collection<GameObject> deadBook;

  /**Places the given <CODE>GameObject</CODE> in the <CODE>deadBook</CODE>.
   * 
   * @param o A object that is to be put in the <code>deadBook<code>. In
   * other words to be removed from the game.
   * @see shipgame.Game#deadBook
   */
  synchronized void putInTheBook(GameObject o)
  {
    deadBook.add(o);
  }

  /**Add a given <CODE>GameObject</CODE> the the game by adding it to 
   * <code>objectCollection<code>. 
   * 
   * @param o A object that is to be added to <code>objectCollection<code>.
   * @see shipgame.Game#objectCollection
   */
  synchronized void addGameObject(GameObject o)
  {
    objectCollection.add(o);
  }
  /**An instance of a player controlled ship.
   */
  private Ship player1Ship,
          player2Ship;

  /** Creates a new instance a <code>Game</code> object. 
   */
  public Game()
  {
    /* Call the JFrace constructor and set the title of the window to 
     * "The Ship Game".
     */
    super("The Ship Game");
    setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    /* For my convenience we do not allow the user to change the size of the
     * window.
     */
    setResizable(false);
    getContentPane().setBackground(BACKGROUND);

    /* objectCollection and deadBook could have been any type of collection.
     * As we are not going to be doing any searches only processing objects in
     * turn a linked list is a suitable collection.
     */
    objectCollection = new LinkedList<GameObject>();
    deadBook = new LinkedList<GameObject>();

    // We must request focus to receive key events.
    requestFocus();

    /* Here I am using an anonymous class to handle key events. Anonymous
     * classes are very useful for listeners. They can be useful in many
     * situations were you need a small class or are only going to make only one
     * instance of a class (why give it a name let alone its own file).
     */
    addKeyListener(new KeyAdapter()
    {
      /* If a key is pressed check to see if it is a command key. If it is 
       * toggle the appropriate ship variable to let it know it state has 
       * changed.
       */
      @Override
      public void keyPressed(KeyEvent ke)
      {
        switch (ke.getKeyCode())
        {
          case KeyEvent.VK_S:
            player1Ship.turningLeft = true;
            break;
          case KeyEvent.VK_F:
            player1Ship.turningRight = true;
            break;
          case KeyEvent.VK_A:
            player1Ship.thrusting = true;
            break;
          case KeyEvent.VK_Z:
            player1Ship.firing = true;
            break;

          case KeyEvent.VK_L:
            player2Ship.turningLeft = true;
            break;
          case KeyEvent.VK_QUOTE:
            player2Ship.turningRight = true;
            break;
          case KeyEvent.VK_K:
            player2Ship.thrusting = true;
            break;
          case KeyEvent.VK_COMMA:
            player2Ship.firing = true;
            break;
        }
      }

      /* When a command key is released we must is toggle the appropriate ship
       * variable to let it know it state has changed.
       */
      @Override
      public void keyReleased(KeyEvent ke)
      {
        switch (ke.getKeyCode())
        {
          case KeyEvent.VK_S:
            player1Ship.turningLeft = false;
            break;
          case KeyEvent.VK_F:
            player1Ship.turningRight = false;
            break;
          case KeyEvent.VK_A:
            player1Ship.thrusting = false;
            break;
          case KeyEvent.VK_Z:
            player1Ship.firing = false;
            break;

          case KeyEvent.VK_L:
            player2Ship.turningLeft = false;
            break;
          case KeyEvent.VK_QUOTE:
            player2Ship.turningRight = false;
            break;
          case KeyEvent.VK_K:
            player2Ship.thrusting = false;
            break;
          case KeyEvent.VK_COMMA:
            player2Ship.firing = false;
            break;

          /* Setup a new game when an N is pressed and the current game is 
           * over.
           */
          case KeyEvent.VK_N:
            if (winner != 0)
            {
              setUp();
            }
            break;
        }
      }
      /* From the Java API: "Key typed" events are higher-level and generally do
       * not depend on the platform or keyboard layout. They are generated when
       * a Unicode character is entered, and are the preferred way to find out
       * about character input. In the simplest case, a key typed event is
       * produced by a single key press (e.g., 'a'). Often, however, characters
       * are produced by series of key presses (e.g., 'shift' + 'a'), and the
       * mapping from key pressed events to key typed events may be many-to-one
       * or many-to-many.
       * 
       * In this case we do not care about such events so we will not override 
       * the keyTyped method. 
       */
      //public void keyTyped(KeyEvent ke){}
    });

    // We want to end the program (exit) when this window is closed;
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    // Call setup to initialize the game.
    setUp();
  }

  /**Update is called when the graphics in the window needs to be refreshed. In
   * this case we also update the positions of all of the objects in the game 
   * display them and display other game information. In other words, the 
   * simulation is synchronized with paint. This is fine for simple games but
   * a better design would be to have game state updates independent of 
   * painting.
   */
  @Override
  public synchronized void paint(Graphics g)
  {
    // If we have a winner announce it.
    if (winner != 0)
    {
      // Set the current font to be 28pt monospaced.
      g.setFont(new Font("Monospaced", java.awt.Font.PLAIN, 28));

      // Draw the announcement in the wining players color.
      if (winner == 1)
      {
        g.setColor(player1Ship.color);
      }
      else
      {
        g.setColor(player2Ship.color);
      }

      // Compose the appropriate announcement.
      String temp = "Game Over Player " + winner + " wins!";

      /* Draw the announcement centered near the top of the window. Use the 
       * current font metric to get the width of the sting to center it.
       */
      g.drawString(temp, DEFAULT_WIDTH / 2 - g.getFontMetrics().stringWidth(temp) / 2, 100);

      // Compose the appropriate announcement.
      temp = "To play again press \"N\"";

      // Draw the announcement centered near the top of the window.
      g.drawString(temp,
                   DEFAULT_WIDTH / 2 - g.getFontMetrics().stringWidth(temp) / 2,
                   100 + g.getFontMetrics().getHeight());

      return;
    }

    // No winner... ok then we need to clear the buffer and update.
    g.setColor(BACKGROUND);
    g.fillRect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);

    // Update the players' ships
    player1Ship.update(g);
    player2Ship.update(g);

    // If a player hit the black hole they take some damage.
    if (player1Ship.location.distance(DEFAULT_WIDTH / 2.0, DEFAULT_HEIGHT / 2.0) < G_DROPOFF)
    {
      player1Ship.iAmHit();
    }
    if (player2Ship.location.distance(DEFAULT_WIDTH / 2.0, DEFAULT_HEIGHT / 2.0) < G_DROPOFF)
    {
      player2Ship.iAmHit();
    }

    // Update all other game objects
    Iterator<GameObject> i = objectCollection.iterator();
    while (i.hasNext())
    {
      i.next().update(g);
    }

    // Remove all of the dead objects from the game.
    objectCollection.removeAll(deadBook);
    // Empty the dead book
    deadBook.clear();

    /* Test to see if any object has hit a ship. This is crude collision 
     * detection. We look to see if the bounding box of a GameObject currently
     * intersects the polygon of a ship. One problem is that if a ship or object
     * is traveling fast enough they could "jump" passed each other even though
     * they should have collided. There is also the the fact that we are using 
     * the bounding box of a object to test for collision. However, all of our
     * GameObjects are relatively small so this should not be too noticeable.
     */
    i = objectCollection.iterator();
    while (i.hasNext())
    {
      GameObject go = i.next();
      if (player1Ship.currentPolygon.intersects(go.currentPolygon.getBounds2D()))
      {
        // Tell the GameObject they hit a ship.
        go.hit(player1Ship);
      }
      if (player2Ship.currentPolygon.intersects(go.currentPolygon.getBounds2D()))
      {
        // Tell the GameObject they hit a ship.
        go.hit(player2Ship);
      }
    }

    // Anyone dead? In case of a "tie" player 2 wins... 
    if (player1Ship.isDead())
    {
      winner = 2;
    }
    else if (player2Ship.isDead())
    {
      winner = 1;
    }

    // Do a random check to see if we should make a new power up.
    if (PowerUp.RAND.nextInt(PowerUp.POWERUP_CHANCE) == 0)
    {
      addGameObject(new PowerUp(this));
    }

    // draw the "black" hole
    g.setColor(Color.white);
    g.drawOval((int) (DEFAULT_WIDTH / 2.0) - (int) (G_DROPOFF / 2.0),
               (int) (DEFAULT_HEIGHT / 2.0) - (int) (G_DROPOFF / 2.0),
               (int) G_DROPOFF,
               (int) G_DROPOFF);

    // Draw the status messages: life, score, and ammo.
    g.setColor(Color.white);
    player1String =
    "Player 1 Life: " + player1Ship.hits()
    + " Score: " + player1Score
    + " Ammo: "
    + ((player1Ship.powerLevel() + 1) * Ship.MAX_NUMBER_OF_SHOTS - player1Ship.shots());

    player2String =
    "Player 2 Life: " + player2Ship.hits()
    + " Score: " + player2Score
    + " Ammo: "
    + ((player2Ship.powerLevel() + 1) * Ship.MAX_NUMBER_OF_SHOTS - player2Ship.shots());

    g.drawString(player1String, 50, 50);
    g.drawString(player2String,
                 DEFAULT_WIDTH - 50 - g.getFontMetrics().stringWidth(player2String),
                 50);
  }

  public final synchronized void setUp()
  {
    if (winner == 1)
    {
      player1Score++;
    }
    else if (winner == 2)
    {
      player2Score++;
    }
    winner = 0;

    objectCollection.clear();

    player1Ship = new Ship(this);
    player2Ship = new Ship(this);
    player1Ship.setLocation(new Position(100, 100));
    player1Ship.color = Color.BLUE;
    player2Ship.setLocation(new Position(DEFAULT_WIDTH - 100, DEFAULT_HEIGHT - 100));
    player2Ship.setOrientation(Math.PI);
    player2Ship.color = Color.RED;
  }

  /**Main creates a new instance of Game, makes it visible, sets the starting
   * locations, and starts the game. 
   *
   * @param args the command line arguments which in this case are completely
   * ignored.
   */
  public static void main(String args[])
  {
    // invokeLater runs on the EDT.
    SwingUtilities.invokeLater(new Runnable()
    {
      @Override
      public void run()
      {
        Game game = new Game();
        game.setVisible(true);
        GraphicsThread gt = new GraphicsThread(game, 17); // 1/60 s ~ 16.67ms
        gt.start();
      }
    });
  }
}
