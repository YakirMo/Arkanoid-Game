package game;

import basic.Counter;
import basic.HitListener;

/**
 * A class that represents a score tracking listener (tracks the blocks being hit and adds score accordingly).
 */
public class ScoreTrackingListener implements HitListener {
    private Counter score;

    /**
     * constructor.
     *
     * @param score - the score to assign to a player.
     */
    public ScoreTrackingListener(Counter score) {
        this.score = score;
    }

    /**
     * A method that determines how many points add whenever a block is being hit.
     *
     * @param beingHit - A block being hit
     * @param hitter   - A ball hitting a block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitsCount() != 0) {
            this.score.increase(5);
        } else {
            this.score.increase(10);
        }
    }

}
