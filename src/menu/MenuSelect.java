package menu;

import levels.LevelInformation;

import java.util.List;

/**
 * The type Menu select.
 */
public class MenuSelect {
    private String key;
    private String message;
    private List<LevelInformation> levels;

    /**
     * Instantiates a new Menu select.
     *
     * @param key     the key
     * @param message the message
     * @param levels  the levels
     */
    public MenuSelect(String key, String message, List<LevelInformation> levels) {
        this.key = key;
        this.message = message;
        this.levels = levels;
    }

    /**
     * Gets key.
     *
     * @return the key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Gets levels.
     *
     * @return the levels
     */
    public List<LevelInformation> getLevels() {
        return this.levels;
    }
}
