package shipgame;

import java.awt.*;

/**An abstract base class for all of the interactive "objects" in the game.
 *
 * @author Andrew Mertz
 */
abstract class GameObject
{
  /**Creates a new instance of <code>GameObject</code>.
   * 
   * @param game The game to which this object belongs.  
   */
  GameObject(Game game)
  {
    this.game = game;
  }
  
  /**The color this object should be drawn in when rendered to the screen. It
   * defaults to white.
   */
  Color color = Color.white;
  
  /**The game to which this object belongs.
   */
  Game game;
  
  /**An array containing the indexes of <code>model</code> where shots can be
   * fired from.
   *
   * @see shipgame.GameObject#model
   */
  int firePoints[];
  
  /**A polygon containing the model of this object translated to its current
   * game position.
   */
  Polygon currentPolygon = new Polygon();
  
  
  /**A polygon containing the model for this object as it appeared in the last 
   * frame.
   */
  Polygon lastPolygon = new Polygon();
  
  /**The model of the object represented as a sequence of points. The origin 
   * represents the actual position of the object.
   */
  Position model[];
  
  /**The current location of the object in the game.
   */
  Position location = new Position();
  
  /**The location of the object in the last frame.
   */
  Position lastLocation = new Position();
  
  /**The current velocity of the object.
   */
  Position velocity = new Position();
  
  /**The angular orientation of the object. There is no conversion to screen 
   * coordinates. Thus, the angle is measured counter-clockwise with 0 being due 
   * "south" (in space it is all relative anyway). 
   */
  double orientation = 0.0;
  
  /**This function should be overridden so that it updates 
   * <code>currentPolygon</code> and <code>lastPolygon<code> for the next frame.
   *
   * @see shipgame.GameObject#currentPolygon
   * @see shipgame.GameObject#lastPolygon
   */
  abstract void updateVerticies();
  
  /**This function will be called when this object hits a ship. By default we do 
   * nothing.
   *
   * @param s The ship that just hit this object.
   */
  void hit(Ship s){}
 
  /**Update the graphics depicting this object. 
   */
  public abstract void update(Graphics g);
  
  /**Return the current location.
   */
  synchronized Position getLocation()
  {
    return location;
  }
  
  /**Return the location in the last frame.
   */
  synchronized Position getLastLocation()
  {
    return lastLocation;
  }
  
  /**Return the current velocity.
   */
  synchronized Position getVelocity()
  {
    return velocity;
  }
  
  /**Return the orientation of the object.
   */
  synchronized double getOrientation()
  {
    return orientation;
  }
  
  /**Changes location of this object to the specified location and update
   * <code>lastLocation</code> appropriately.
   *
   * @param location The new location for this object.
   */
  synchronized void setLocation(Position location)
  {
    this.lastLocation.setLocation(this.location);
    this.location.setLocation(location);
  }
  
  /**Changes the velocity of the object.
   */
  synchronized void setVelocity(Position velocity)
  {
    this.velocity.setLocation(velocity);
  }
  
  /**The constant 2*pi.
   */
  public static final double TWO_PI = 2.0 * Math.PI;
  
  /**Reduces an angle so that it is between 0 and 2*pi. 
   *
   * @param angle An angle in radians.
   * @return An equivalent angle between 0 and 2*pi.
   */
  public static double reduceAngle(double angle)
  {
    if(angle > TWO_PI)
      return angle - Math.floor(angle/TWO_PI) * TWO_PI;
    
    if(angle < 0)
      return angle - Math.ceil(angle/TWO_PI) * TWO_PI;
    
    return angle;
  }
  
  /**Changes the orientation of the object.
   *
   * @param orientation An angle in radians.  
   */
  synchronized void setOrientation(double orientation)
  {
    this.orientation = reduceAngle(orientation);
  }
  
  /**Rotates the current object by a given amount. Note that a positive amount 
   * turns to the left and negative to the right. This is due to the fact that
   * there is no conversion from the "standard" axises to screen axises.  
   *
   * @param amount The amount of the turn.
   */
  synchronized void turn(double amount)
  {
    orientation = reduceAngle(orientation + amount);
  }
  
  /**Updates the location of the object bases only on its velocity.
   */
  synchronized void updateLocation()
  {
    lastLocation.setLocation(location);
    location.translate(velocity.x, velocity.y);
  }
}
