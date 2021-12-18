package menu;

import biuoop.GUI;

/**
 * The type Quit task.
 */
public class QuitTask implements Task<Void> {
    private GUI gui;

    /**
     * Instantiates a new Quit task.
     *
     * @param gui the gui
     */
    public QuitTask(GUI gui) {
        this.gui = gui;
    }
    @Override
    public Void run() {
        System.exit(0);
        return null;
    }
}
