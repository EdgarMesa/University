package shipgame;

import java.awt.Graphics;
import java.awt.*;
import java.util.Random;

/**A game object that represents a power up that adds to the number of shots a 
 * ship can fire.
 *
 * @author Andrew Mertz
 */
class PowerUp extends GameObject
{
  /**A random number generator for determining when and where a power up should 
   * be added to the game. 
   */
  static final Random RAND = new Random();
  
  /**The default time a power up lives (time to live). In other words the number
   * of updates until it is put in the book.
   *
   * @see shipgame.Game#putInTheBook 
   */
  static final int TTL = 660;
  
  /**The default time a power up is applied to a ship. In other words the number
   * of updates until the ship powers down.
   */
  static final int POWER_TTL = 2400;

  /**A power up has a 1 in <code>POWERUP_CHANCE</code> chance of being added to 
   * the game each update. 
   */
  static final int POWERUP_CHANCE = 600;
  
  /**The current time to live of a power up.
   */
  int ttl = TTL;
  
  /**The 2D model of a power up given as an array of positions. In this case it 
   * is a diamond pattern.
   */
  Position model[] = {new Position( 0.0,  5.0),
                      new Position(-5.0,  0.0),
                      new Position( 0.0, -5.0),
                      new Position( 5.0,  0.0)};

  /**Applies a power up to the ship that hit this object and removes this object 
   * from the game.
   */                    
  void hit(Ship s)
  {
    s.gotPowerUp();
    ttl = 0;
  }

  /**Updates the polygons of this object based on the current velocity. Note 
   * that the black hole force does not effect power ups. Also, right now power 
   * ups always have zero velocity so it is not going anywhere.
   *
   * @see shipgame.GameObject#currentPolygon
   * @see shipgame.GameObject#lastPolygon
   */
  synchronized void updateVerticies()
  {
    // Store the soon to be out of date location information.
    lastLocation.setLocation(location);
    
    /* We will use the cos and sin of orientation a few times so we will store 
     * them.
     */
    double 
      cos = Math.cos(orientation),
      sin = Math.sin(orientation);

    // Update our location based on our current velocity.
    location.translate(velocity);

    // If location is out of bounds warp to the other side.
    if(location.x < 0.0)
      location.x += game.DEFAULT_WIDTH;
    if(location.y < 0.0)
      location.y += game.DEFAULT_HEIGHT;
    if(location.x > game.DEFAULT_WIDTH)
      location.x -= game.DEFAULT_WIDTH;
    if(location.y > game.DEFAULT_HEIGHT)
      location.y -= game.DEFAULT_HEIGHT;

    // Store a copy of the, now out of date, currentPolygon. 
    lastPolygon = new Polygon(currentPolygon.xpoints,
                              currentPolygon.ypoints,
                              currentPolygon.npoints);
 
    // Clear the current polygon.
    currentPolygon.reset();
    
    /* For each of the vertexes in our model rotate and then translate them into
     * position.
     */
    for(int i = 0; i < model.length; i++)
    {
      /* Note that a rotation matrix looks like:
       *  _             _
       * |               |
       * | cos(t)  sin(t)|
       * |               |
       * |-sin(t)  cos(t)|
       * |_             _|
       *
       * See http://mathworld.wolfram.com/RotationMatrix.html for more details.
       *
       * To translate we just add to each coordinate. Thus we have the 
       * following.
       */
      currentPolygon.addPoint(
        (int)((model[i].getX() * cos + model[i].getY() * sin) + location.x),
        (int)((model[i].getY() * cos - model[i].getX() * sin) + location.y));
    }
  }

  public synchronized void update(Graphics g)
  {
    // Update our location.
    updateVerticies();

    /* If our time to live is less than or equal to 0 or we have hit the black
     * it is time to be put in the book. Also we decrement ttl.
     */
    if(ttl-- <= 0 ||
       location.distance(game.DEFAULT_WIDTH/2.0, game.DEFAULT_HEIGHT/2.0) < Game.G_DROPOFF)
    {
      game.putInTheBook(this);
      return;
    }

    // Set our color and draw our model in the new location.
    g.setColor(color);
    g.drawPolygon(currentPolygon);
  }

  /**Creates a new instance of a <code>PowerUp</code> in a random location with
   * zero velocity.
   * 
   * @param game The game to which this object belongs.  
   */
  PowerUp(Game game)
  {
    super(game);
    
    // Put us in a random location.
    location = new Position(RAND.nextInt(game.DEFAULT_WIDTH), RAND.nextInt(game.DEFAULT_HEIGHT));
    lastLocation = new Position();
  }
}
