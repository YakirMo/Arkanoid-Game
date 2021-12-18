package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The type Key press stoppable animation.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor ks;
    private String key;
    private Animation anim;
    private boolean stop = false;
    private boolean isAlreadyPressed = true;

    /**
     * Instantiates a new Key press stoppable animation.
     *
     * @param sensor    the sensor
     * @param key       the key
     * @param animation the animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.ks = sensor;
        this.key = key;
        this.anim = animation;
    }

    /**
     * Draw the received animation(as constructor argument), check if a key is pressed, if so, stop this animation.
     * and continue the game.
     *
     * @param d the Drawsurface to draw on
     */
    public void doOneFrame(DrawSurface d) {
        this.anim.doOneFrame(d);
        if (this.ks.isPressed(this.key)) {
            if (!isAlreadyPressed) {
                this.stop = true;
            }
        } else {
            isAlreadyPressed = false;
        }
    }

    /**
     * Should stop boolean.
     *
     * @return true or false, whether this animation should be stopped or not
     */
    public boolean shouldStop() {
        return this.stop;
    }
}
