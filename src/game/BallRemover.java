package game;

import basic.Counter;
import basic.HitListener;

/**
 * A class that represents a BallRemover object (responsible of removing a ball when a certain hitEvent occurs).
 */
public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBalls;

    /**
     * constructor.
     *
     * @param game           - A game to add a BallRemover to
     * @param remainingBalls - The amount of balls remaining in a game
     */
    public BallRemover(GameLevel game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    /**
     * A method that removes a ball from a game when a certain hitEvent occurs (A ball hitting the bottom rectangle).
     * @param beingHit - A rectangle that is being hit by a ball (in this case, bottom rectangle)
     * @param hitter   - A ball that hits an object
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        this.remainingBalls.decrease(1);
    }
}
