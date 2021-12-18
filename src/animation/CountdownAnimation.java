package animation;

import biuoop.DrawSurface;
import game.SpriteCollection;
import java.awt.Color;

/**
 * The type Countdown animation.
 */
public class CountdownAnimation implements Animation {
    private boolean running = true;
    private int countFrom;
    private int counter;
    private SpriteCollection gameScreen;
    private long milliSeconds;
    private long initTime;

    /**
     * Instantiates a new Countdown animation.
     *
     * @param numOfSeconds the num of seconds
     * @param countFrom    the count from
     * @param gameScreen   the game screen
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.countFrom = countFrom;
        this.counter = countFrom;
        this.milliSeconds = (long) (numOfSeconds * 1000);
        this.gameScreen = gameScreen;
        this.initTime = System.currentTimeMillis();


    }

    /**
     * Draw a countdown animation for each level.
     *
     * @param d the Drawsurface to draw on
     */
    public void doOneFrame(DrawSurface d) {
        this.gameScreen.drawAllOn(d);
        d.setColor(new Color(255, 204, 102));
        d.drawText(380, 450, Integer.toString(countFrom), 80);
        if (System.currentTimeMillis() - this.initTime > this.milliSeconds / this.counter) {
            this.initTime = System.currentTimeMillis();
            this.countFrom--;
        }
        if (countFrom == 0) {
            this.running = false;
        }
    }

    /**
     * Should stop boolean.
     *
     * @return true or false, whether this animation should be stopped or not
     */
    public boolean shouldStop() {
        return !this.running;
    }
}
