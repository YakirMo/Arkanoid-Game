package game;

import basic.Counter;
import basic.Sprite;
import biuoop.DrawSurface;
import geo.Rectangle;
import java.awt.Color;


/**
 * This class represents LivesIndicator (an object responsible of representing the amount of lives left in a game).
 */
public class LivesIndicator implements Sprite {
    private Counter lives;
    private geo.Rectangle livesInd;

    /**
     * constructor.
     * creates a counter that represents amount of lives and creates a rectangle to present it graphically.
     *
     * @param lives - amount of lives to assign to a player (in a game session)
     */
    public LivesIndicator(Counter lives) {
        this.lives = lives;
        this.livesInd = new Rectangle(0, 0, 15, 200);
    }

    /**
     * A method to draw the LivesIndicator on a given DrawSurface.
     * @param d - the DrawSurface to draw on
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.gray.brighter());
        d.fillRectangle((int) this.livesInd.getUpperLeft().getX(),
                (int) this.livesInd.getUpperLeft().getY(),
                (int) this.livesInd.getWidth(),
                (int) this.livesInd.getHeight());
        d.setColor(Color.black);
        String livesToDraw = "Lives: " + (this.lives.getValue());
        d.drawText((int) (this.livesInd.getUpperLeft().getX() + this.livesInd.getWidth() / 2),
                (int) (this.livesInd.getUpperLeft().getY() + this.livesInd.getHeight() / 2 + 15), livesToDraw, 15);

    }

    /**
     * A method that adds a livesIndicator to a game.
     * @param game - A game to add the livesIndicator to
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }

    /**
     * Unused method. (Sprite class).
     */
    public void timePassed() { }

}
