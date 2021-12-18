import animation.AnimationRunner;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import filesio.LevelSetReader;
import highscore.HighScoresTable;
import menu.StartGame;
import menu.Task;
import menu.Menu;
import menu.MenuAnimation;
import menu.MenuSelect;
import menu.ShowHiScoresTask;
import menu.QuitTask;
import java.io.File;
import java.util.List;

/**
 *
 * @author Yakir Moshe <YakirMoshe@gmail.com> this class creates an instance of a game object which is a simple
 * version of the Arkanoid game.
 */
public class Ass7Game {
    /**
     * the main method of the program.
     * initialize's a game instance and run's it.
     *
     * @param args - levels to run
     */
    public static void main(String[] args) {
        GUI gui = new GUI("Arkanoid", 800, 600);
        AnimationRunner ar = new AnimationRunner(gui);
       // Counter counter = new Counter(0);
        KeyboardSensor key = gui.getKeyboardSensor();
       // List<LevelInformation> levels = new ArrayList<>();
        File highScoresFile = new File("highscores");
        HighScoresTable highScoresTable = HighScoresTable.loadFromFile(highScoresFile);
        Menu<Task<Void>> subMenu = new MenuAnimation<Task<Void>>(
                "Level Sets", gui.getKeyboardSensor(), ar);
        List<MenuSelect> levelSet;
        levelSet = LevelSetReader.fromReader(args);
        for (MenuSelect select: levelSet) {
            subMenu.addSelection(select.getKey(), select.getMessage(), new StartGame(gui, ar, highScoresTable,
                    select.getLevels(), 7, highScoresFile));

        }
        Menu<Task<Void>> menu = new MenuAnimation("Arkanoid", key, ar);
        menu.addSubMenu("s", "Start game", subMenu);
        menu.addSelection("h", "High Scores", new ShowHiScoresTask(ar, gui.getKeyboardSensor(), highScoresTable));
        menu.addSelection("e", "Exit", new QuitTask(gui));
        while (true) {
            ar.run(menu);
            Task<Void> task = menu.getStatus();
            task.run();
            menu.resrtStatus();
        }
    }
}