package animation;

import basic.Counter;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The type End screen.
 */
public class EndScreen implements Animation {
    private int score;
    private boolean win;
    private boolean close;

    /**
     * Instantiates a new End screen.
     *
     * @param score the score
     * @param win   the win
     */
    public EndScreen(Counter score, boolean win) {
        this.score = score.getValue();
        this.win = win;
        this.close = false;
    }

    /**
     * Draw an end screen animation.
     *
     * @param d the Drawsurface to draw on
     */
    public void doOneFrame(DrawSurface d) {
        d.setColor(new Color(20, 85, 87));
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(Color.black);
        if (this.win) {
            d.drawText(130, 200, "Well Played!", 80);
            d.setColor(new Color(129, 51, 243));
            d.drawText(134, 196, "Well Played!", 80);
            d.setColor(Color.black);
            d.drawText(210, 280, "You Won :)", 60);
            d.setColor(new Color(148, 75, 133));
            d.drawText(214, 276, "You Won :)", 60);
        } else {
            d.drawText(130, 200, "Game Over", 80);
            d.setColor(new Color(243, 49, 53));
            d.drawText(134, 196, "Game Over", 80);
            d.setColor(Color.black);
            d.drawText(210, 280, "You Lost :/", 60);
            d.setColor(new Color(148, 75, 133));
            d.drawText(214, 276, "You Lost :/", 60);
        }
        d.setColor(Color.black);
        d.drawText(250, 370, "Press space to continue...", 25);
        d.setColor(Color.white);
        d.drawText(320, 430, "Final score: " + this.score, 20);
    }

    /**
     * Should stop boolean.
     *
     * @return true or false, whether this animation should be stopped or not
     */
    public boolean shouldStop() {
        return this.close;
    }
}
