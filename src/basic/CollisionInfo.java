package basic;
import geo.Point;

/**
 * This class creates a Collision info instance that holds the collision point and the collided object.
 */
public class CollisionInfo {
    private Point collisionP;
    private Collidable collisionObj;

    /**
     * constructor.
     *
     * @param collisionP    - the collision point
     * @param collidableObj - the collision object
     */
    public CollisionInfo(Point collisionP, Collidable collidableObj) {
        this.collisionObj = collidableObj;
        this.collisionP = collisionP;
    }

    /**
     * This method return the collided object.
     *
     * @return - the collided object
     */
    public Collidable getCollisionObj() {
        return this.collisionObj;
    }

    /**
     * This method returns the collision point.
     *
     * @return - the collision point
     */
    public Point getCollisionP() {
        return this.collisionP;
    }
}