package game;
import basic.HitListener;
import basic.Counter;

/**
 * A class that represents a BlockRemover object (responsible of removing a block when a certain hitEvent occurs).
 */
public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * constructor.
     *
     * @param game            - A game to add a BlockRemover to
     * @param remainingBlocks - The amount of blocks remaining in a game
     */
    public BlockRemover(GameLevel game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * A method that removes a block from a game when a certain hitEvent occurs (A block being hit by a ball).
     * @param beingHit - A block that is being hit by a ball
     * @param hitter   - A ball that hits a block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitsCount() == 0) {
            beingHit.removeHitListener(this);
            beingHit.removeFromGame(this.game);
            this.remainingBlocks.decrease(1);
        }
    }


}
