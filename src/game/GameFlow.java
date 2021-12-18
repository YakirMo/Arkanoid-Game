package game;

import animation.AnimationRunner;
import animation.EndScreen;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import basic.Counter;
import biuoop.KeyboardSensor;
import highscore.HighScoresTable;
import highscore.ScoreInfo;
import levels.LevelInformation;
import java.util.List;
import biuoop.DialogManager;

/**
 * The type Game flow.
 */
public class GameFlow {
    private KeyboardSensor key;
    private AnimationRunner runner;
    private Counter lives;
    private Counter score;
    private boolean win;
    private HighScoresTable table;
    private DialogManager dm;

    /**
     * Instantiates a new Game flow.
     *
     * @param ar    the ar
     * @param ks    the ks
     * @param lives the lives
     * @param table the table
     * @param dm    the dm
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, int lives, HighScoresTable table, DialogManager dm) {
        this.runner = ar;
        this.key = ks;
        this.lives = new Counter(lives);
        this.score = new Counter(0);
        this.table =  table;
        this.dm = dm;
    }

    /**
     * Run levels.
     * Thus, by initializing indicators (score, lives, levels names'), and checking lives value to determine whether
     * it's a win or a lose
     *
     * @param levels the levels
     */
    public void runLevels(List<LevelInformation> levels) {
        for (LevelInformation levelInfo : levels) {
            GameLevel level = new GameLevel(levelInfo, this.key, this.runner, this.score, this.lives);
            level.initialize();
            ScoreIndicator scoreIndicator = new ScoreIndicator(this.score);
            LivesIndicator livesIndicator = new LivesIndicator(this.lives);
            NamesIndicator name = new NamesIndicator(levelInfo.levelName());
            level.addSprite(scoreIndicator);
            level.addSprite(livesIndicator);
            level.addSprite(name);
            while (this.lives.getValue() > 0) {
                level.playOneTurn();
                if (level.win()) {
                    this.score.increase(100);
                    this.win = true;
                    break;
                } else {
                    this.lives.decrease(1);
                }
            }
            if (this.lives.getValue() == 0) {
                this.win = false;
                break;
            }
        }
        if (this.table.getRank(this.score.getValue()) <= this.table.size()) {
            String name = dm.showQuestionDialog("Name", "Enter your name:", "");
            ScoreInfo scoreInf = new ScoreInfo(name, this.score.getValue());
            this.table.add(scoreInf);
        }
        this.runner.run(new KeyPressStoppableAnimation(this.key, KeyboardSensor.SPACE_KEY,
                new EndScreen(this.score, this.win)));
        this.runner.run(new KeyPressStoppableAnimation(this.key, KeyboardSensor.SPACE_KEY,
                new HighScoresAnimation(this.table)));
    }
}
