package levels;

import basic.Sprite;
import basic.Velocity;
import game.Block;

import java.util.List;

/**
 * The interface Level information.
 */
public interface LevelInformation {
    /**
     * Number of balls to initialize.
     *
     * @return the number of balls
     */
    int numberOfBalls();

    /**
     * Initial balls' velocities list.
     *
     * @return the velocities list
     */
// The initial velocity of each ball
    // Note that initialBallVelocities().size() == numberOfBalls()
    List<Velocity> initialBallVelocities();

    /**
     * initialize Paddle speed.
     *
     * @return the paddle speed
     */
    int paddleSpeed();

    /**
     * initialize Paddle width.
     *
     * @return the paddle width
     */
    int paddleWidth();

    /**
     * Level name.
     *
     * @return the level's name
     */
// the level name will be displayed at the top of the screen.
    String levelName();

    /**
     * Gets level background.
     *
     * @return the background
     */
// Returns a sprite with the background of the level
    Sprite getBackground();

    /**
     * Blocks' list of a level.
     *
     * @return the list of blocks
     */
// The Blocks that make up this level, each block contains
    // its size, color and location.
    List<Block> blocks();

    /**
     * Number of blocks to remove.
     *
     * @return the amount of blocks to remove
     */
// Number of levels that should be removed
    // before the level is considered to be "cleared".
    // This number should be <= blocks.size();
    int numberOfBlocksToRemove();
}
