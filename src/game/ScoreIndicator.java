package game;

import basic.Counter;
import basic.Sprite;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * This class represents ScoreIndicator (an object responsible of representing the amount of scores earned in a game).
 */
public class ScoreIndicator implements Sprite {
    private Counter score;
    private geo.Rectangle scoreInd;

    /**
     * constructor.
     * creates a counter that represents amount of scores and creates a rectangle to present it graphically.
     *
     * @param score - amount of scores a player earned(in a game session)
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
        this.scoreInd = new geo.Rectangle(200, 0, 15, 325);
    }

    /**
     * A method to draw the ScoreIndicator on a given DrawSurface.
     *
     * @param d - the DrawSurface to draw on
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.gray.brighter());
        d.fillRectangle((int) this.scoreInd.getUpperLeft().getX(),
                (int) this.scoreInd.getUpperLeft().getY(),
                (int) this.scoreInd.getWidth(),
                (int) this.scoreInd.getHeight());
        d.setColor(Color.black);
        String scores = "Score: " + (this.score.getValue());
        d.drawText((int) (this.scoreInd.getUpperLeft().getX() + this.scoreInd.getWidth() / 2),
                (int) (this.scoreInd.getUpperLeft().getY() + this.scoreInd.getHeight() / 2 + 15), scores, 15);
    }

    /**
     * A method that adds a ScoreIndicator to a game.
     *
     * @param game - A game to add the livesIndicator to
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
    }

    /**
     * Unused method. (Sprite class).
     */
    public void timePassed() {
    }
}
