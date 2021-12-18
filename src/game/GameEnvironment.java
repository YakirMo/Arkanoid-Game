package game;
import basic.Collidable;
import basic.CollisionInfo;
import geo.Line;
import geo.Point;
import java.util.ArrayList;

/**
 * This class creates a GameEnvironment instance that determines about close collisions.
 */
public class GameEnvironment {
    private ArrayList<Collidable> collidables;

    /**
     * constructor.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }

    /**
     * This methods adds a collidable object to collidables ArrayList.
     *
     * @param c - the collidable object to add
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * This method gets the closest collision to occur between the ball and an object.
     *
     * @param trajectory - the ball's trajectory
     * @return - the info of a future collision (collision point and collision object)
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        int i, size;
        CollisionInfo collInf;
        Point p = null;
        size = this.collidables.size();
        for (i = 0; i < size; i++) {
            p = trajectory.closestIntersectionToStartOfLine(
                    this.collidables.get(i).getCollisionRectangle());
            if (p != null) {
                break;
            }
        }
        if (p == null) {
            return null;
        }
        collInf = new CollisionInfo(p, this.collidables.get(i));
        return collInf;
    }

    /**
     * This method returns the collidable ArrayList.
     *
     * @return - ArraryList of collidables
     */
    public ArrayList<Collidable> getColList() {
        return this.collidables;
    }
}