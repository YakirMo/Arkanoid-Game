package menu;

import animation.AnimationRunner;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import biuoop.KeyboardSensor;
import highscore.HighScoresTable;

/**
 * The type Show hi scores task.
 */
public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private KeyboardSensor ks;
    private HighScoresTable table;

    /**
     * Instantiates a new Show hi scores task.
     *
     * @param ar    the ar
     * @param ks    the ks
     * @param table the table
     */
    public ShowHiScoresTask(AnimationRunner ar, KeyboardSensor ks, HighScoresTable table) {
        this.runner = ar;
        this.ks = ks;
        this.table = table;
    }
    @Override
    public Void run() {
        this.runner.run(new KeyPressStoppableAnimation(this.ks, KeyboardSensor.SPACE_KEY,
                new HighScoresAnimation(table)));
        return null;
    }
}
