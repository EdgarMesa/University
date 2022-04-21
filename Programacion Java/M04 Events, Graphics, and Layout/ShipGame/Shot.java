package shipgame;

import java.awt.*;

/**A shot fired from a GameObject.
 *
 * @author Andrew Mertz
 */
class Shot extends GameObject
{
  /**The normal speed of a shot.
   */
  public static final double SHOT_SPEED = 10.1;
  
  /**The default number of frames a shot is in play. In other words the number
   * of updates until it is put in the book.
   *
   * @see shipgame.Game#putInTheBook 
   */
  public static final int TTL = 60;
  
  /**The current time to live.
   */
  private int ttl = TTL;
  
  /**Who shot us.
   */
  Ship shooter;
  
  /**The 2D model of a shot given as an array of positions. In this case it 
   * is a mini v pattern.
   */
  Position model[] = {new Position( 0.0,  2.0),
                      new Position(-1.0, -1.0),
                      new Position( 0.0,  0.0),
                      new Position( 1.0, -1.0)};

  /** Creates a new instance of Shot 
   *
   * @param game The game we are in.
   * @param shooter The ship that fired this shot.
   * @param firingPoint Where the shot originated from.
   */
  Shot(Game game, Ship shooter, Position firingPoint)
  {
    super(game);
    this.shooter = shooter;
    this.orientation = shooter.orientation;
    this.location = new Position(firingPoint);
    
    // Decompose shot speed into the x and y coordinates.
    this.velocity = new Position(SHOT_SPEED * Math.sin(orientation),
                                 SHOT_SPEED * Math.cos(orientation));
  }

  synchronized void updateVerticies()
  {
    /* We will use the cos and sin of orientation a few times so we will store 
     * them.
     */
    double 
      cos = Math.cos(orientation),
      sin = Math.sin(orientation);

    // Apply the black hole force
    velocity.translate(Game.gForce(this));
    
    /* Do not let the black hole accelerate us past 2 the normal maximum speed.
     * Maybe we should have another constant for this...
     */
    if(velocity.magnitude() > 2.0 * SHOT_SPEED)
    {
      // If we are over the limit scale our velocity back.
      double factor = 2.0 * SHOT_SPEED / velocity.magnitude();
      velocity.x *= factor;
      velocity.y *= factor;
    }

    // Update our location based on our current velocity.
    lastLocation.setLocation(location);
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

  /**If we hit a ship other then the one that fired us we damage it and remove 
   * this shot from the game.
   */
  void hit(Ship s)
  {
    if(s != shooter)
    {
      s.iAmHit();
      ttl = 0;
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
       location.distance(game.DEFAULT_WIDTH/2.0,game.DEFAULT_HEIGHT/2.0)<Game.G_DROPOFF * 0.75)
    {
      game.putInTheBook(this);
      shooter.shotLost();
      return;
    }

    // Set our color and draw our model in the new location.
    g.setColor(color);
    g.drawPolygon(currentPolygon);
  }
}
