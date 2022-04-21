package shipgame;

import java.awt.*;

/**One of the combatants in the game.
 *
 * @author Andrew Mertz
 */
class Ship extends GameObject
{
  /**The force a ship's engines can exert each frame.
   */
  static final double THRUST = 0.1;
  
  /**The maximum magnitude of the velocity of a ship that normal thrust can take
   * it to.
   */
  static final double MAX_SPEED = 10.0;
  
  /**The amount to turn a ship by each frame it is turning. 
   */
  static final double TURN_RATE = Math.toRadians(5);
  
  /**The maximum number of shots a ship can have out at one time, before power 
   * ups.
   */
  static final int MAX_NUMBER_OF_SHOTS = 30;
  
  /**Starting amount of life of a ship.
   */
  static final int  STARTING_HITS = 20;
  
  /**Current number of shots in flight.
   */
  private int numberOfShots = 0;
  
  /**Current power level (number of power ups active).
   */
  private int powerUp = 0;
  
  /**Time to live of current power up.
   */
  private int powerTTL = 0;
  
  /**Current life.
   */
  private int hits = STARTING_HITS;
  
  /**An array containing the indexes of <code>model</code> where shots can be
   * fired from.
   *
   * @see shipgame.GameObject#model
   */
  private int firePoints[] = {0,1,3};
  
  /**Are we currently turning left.
   */
  boolean turningLeft;
  
  /**Are we currently turning right.
   */
  boolean turningRight;
  
  /**Are we currently thrusting.
   */
  boolean thrusting;
  
  /**Are we currently firing.
   */
  boolean firing;
  
  /**The 2D model of a ship given as an array of positions. In this case it 
   * is a V like pattern.
   */
  Position model[] = {new Position(0.0, 13.0),
                      new Position(-6.0, -6.0),
                      new Position(0.0,0.0),
                      new Position(6.0, -6.0)};

  /**Called when one of this ship's shots are removed from the game.
   */                    
  void shotLost()
  {
    numberOfShots--;
  }

  /**Return the current life of the ship.
   */
  public int hits()
  {
    return hits;
  }

  /**Are we dead?
   */
  public boolean isDead()
  {
    return hits <= 0;
  }

  /**Decrements this ship's life by one. 
   */
  void iAmHit()
  {
    hits--;
  }

  /**Returns the number of power ups active on this ship.
   */
  int powerLevel()
  {
    return powerUp;
  }

  /**Called when we get a power up.
   */
  void gotPowerUp()
  {
    // Increase our power level.
    powerUp++;
    
    // Reset the timer.
    powerTTL = PowerUp.POWER_TTL;
  }

  // How many shots do we have in flight.
  int shots()
  {
    return numberOfShots;
  }

  /**Updates the polygons of this object based on the current velocity and black
   * hole force.
   *
   * @see shipgame.GameObject#currentPolygon
   * @see shipgame.GameObject#lastPolygon
   * @see shipgame.Game#gForce
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

    // Are we turning?
    if(turningLeft)
    {
      if(!turningRight)
        turn(TURN_RATE);
    }
    else if (turningRight)
    {
      turn(-TURN_RATE);
    }

    if(thrusting)
    {
      // If we are thrusting we need to update our velocity.
      Position temp = new Position(velocity);
      
      // Decompose the force of our thrust into the x and y coordinates.
      temp.translate(THRUST * sin, THRUST * cos);
      
      // Make sure we are obeying the speed limit.
      if(temp.magnitude() <= MAX_SPEED ||
         temp.magnitude() <= velocity.magnitude())
        velocity.setLocation(temp);
    }

    // Apply the black hole force
    velocity.translate(Game.gForce(this));
    
    /* Do not let the black hole accelerate us past 2 the normal maximum speed.
     * Maybe we should have another constant for this...
     */
    if(velocity.magnitude() > 2.0 * MAX_SPEED)
    {
      // If we are over the limit scale our velocity back.
      double factor = 2.0 * MAX_SPEED / velocity.magnitude();
      velocity.x *= factor;
      velocity.y *= factor;
    }

    // If we are firing and still have ammo let fly.
    if(firing && numberOfShots < (powerUp + 1) * MAX_NUMBER_OF_SHOTS)
    {
      // Firing point one is active at power level 0 and 2+ 
      if(powerUp == 0 || powerUp >= 2)
      {
        game.addGameObject(
          new Shot(game,
                   this,
                   new Position(currentPolygon.xpoints[firePoints[0]],
                                currentPolygon.ypoints[firePoints[0]])));
        numberOfShots++;
      }
      
      // Firing points 2 and 3 are active at power level 1+
      if(powerUp > 0)
      {
        game.addGameObject(
          new Shot(game,
                   this,
                   new Position(currentPolygon.xpoints[firePoints[1]],
                                currentPolygon.ypoints[firePoints[1]])));
        game.addGameObject(
          new Shot(game,
                   this,
                   new Position(currentPolygon.xpoints[firePoints[2]],
                                currentPolygon.ypoints[firePoints[2]])));
        numberOfShots += 2;
      }
    }

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

    // Set our color and draw our model in the new location.
    g.setColor(color);
    g.drawPolygon(currentPolygon);

    /* Decrement the TTL for any power ups and check to see if the time on the 
     * current power up has run out. If so decrement the power level and reset
     * the clock on the power up.
     */ 
    if(powerTTL > 0)
      powerTTL--;
    else if(powerUp > 0)
    {
      powerUp--;
      powerTTL = PowerUp.POWER_TTL;
    }
  }

  /** Creates a new instance of Ship */
  Ship(Game game)
  {
    super(game);
    turningLeft = turningRight = thrusting = firing = false;
  }
}
