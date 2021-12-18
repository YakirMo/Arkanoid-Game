package menu;

import animation.AnimationRunner;
import biuoop.GUI;
import game.GameFlow;
import highscore.HighScoresTable;
import levels.LevelInformation;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The type Start game.
 */
public class StartGame implements Task<Void> {
    private GUI gui;
    private AnimationRunner runner;
    private HighScoresTable table;
    private List<LevelInformation> levels;
    private int lives;
    private File highScoreFile;

    /**
     * Instantiates a new Start game.
     *
     * @param gui           the gui
     * @param ar            the ar
     * @param table         the table
     * @param levels        the levels
     * @param lives         the lives
     * @param highScoreFile the high score file
     */
    public StartGame(GUI gui, AnimationRunner ar, HighScoresTable table, List<LevelInformation> levels,  int lives,
                     File highScoreFile) {
        this.gui = gui;
        this.runner = ar;
        this.table = table;
        this.levels = levels;
        this.lives = lives;
        this.highScoreFile = highScoreFile;
    }
    @Override
    public Void run() {
        GameFlow game = new GameFlow(this.runner, this.gui.getKeyboardSensor(), this.lives, this.table,
                this.gui.getDialogManager());
        game.runLevels(levels);
        try {
            table.save(this.highScoreFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
