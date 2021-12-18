package basic;
import geo.Point;
import geo.Rectangle;
import game.Ball;

/**
 * This interface is used to define a collidable object in the game.
 */
public interface Collidable {
    /**
     * This method returns the rectangle a collision occurred with.
     *
     * @return - the collided rectangle
     */
    Rectangle getCollisionRectangle();

    /**
     * This method change's an object's velocity depending on the collision occurred.
     *
     * @param collisionPoint  - collision point with another object
     * @param currentVelocity - current velocity of the object
     * @param hitter          - A ball that hits an object
     * @return - new velocity of the object
     */
    Velocity hit(Point collisionPoint, Velocity currentVelocity, Ball hitter);
}