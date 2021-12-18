package basic;
import game.GameLevel;
import biuoop.DrawSurface;

/**
 * This interface represents the behaviour of objects in the game.
 */
public interface Sprite {
    /**
     * This method draws a sprite to the screen.
     *
     * @param d - the DrawSurface to draw on
     */
    void drawOn(DrawSurface d);

    /**
     * This method notifies sprite objects the a time unit has passed.
     */
    void timePassed();

    /**
     * This method add a sprite object to the game.
     *
     * @param g - the game to add a sprite to
     */
    void addToGame(GameLevel g);
}