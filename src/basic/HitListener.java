package basic;
import game.Ball;
import game.Block;

/**
 * An interface that represents listeners (used by blocks, balls, etc).
 */
public interface HitListener {

    /**
     * This method is called whenever an object is being hit.
     *
     * @param beingHit - the object that being hit.
     * @param hitter   - the object that hit's (the ball in our case).
     */
    void hitEvent(Block beingHit, Ball hitter);
}
