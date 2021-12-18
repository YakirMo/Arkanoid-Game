package animation;

import biuoop.DrawSurface;
import game.Fill;
import java.awt.Image;
import java.awt.Color;

/**
 * The type Pause screen.
 */
public class PauseScreen implements Animation {
    private Image pauseImage;

    /**
     * Instantiates a new Pause screen.
     */
    public PauseScreen() {
        this.pauseImage = Fill.stringToImage("Image/pauseScreen.jpg");
    }

    /**
     * Draw a pause screen animation.
     *
     * @param d the Drawsurface to draw on
     */
    public void doOneFrame(DrawSurface d) {
        d.drawImage(0, 0, pauseImage);
        d.setColor(Color.black);
        d.drawText(210, 500, "Press space to continue", 40);
    }

    /**
     * unused.
     *
     * @return false
     */
    public boolean shouldStop() {
        return false;
    }
}
