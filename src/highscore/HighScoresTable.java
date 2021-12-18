package highscore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type High scores table.
 */
public class HighScoresTable implements Serializable {
    private List<ScoreInfo> highScores;
    private int size;

    /**
     * Instantiates a new High scores table.
     *
     * @param size the size
     */
// Create an empty high-scores table with the specified size.
    // The size means that the table holds up to size top scores.
    public HighScoresTable(int size) {
        this.highScores = new ArrayList<ScoreInfo>();
        this.size = size;
    }

    /**
     * Add.
     *
     * @param score the score
     */
// Add a high-score.
    public void add(ScoreInfo score) {
        this.highScores.add(this.getRank(score.getScore()) - 1, score);
        while (this.highScores.size() > this.size()) {
            this.highScores.remove(this.highScores.size() - 1);
        }
    }

    /**
     * Size int.
     *
     * @return the int
     */
// Return table size.
    public int size() {
        return this.size;
    }

    /**
     * Gets high scores.
     *
     * @return the high scores
     */
// Return the current high scores.
    // The list is sorted such that the highest
    // scores come first.
    public List<ScoreInfo> getHighScores() {
        return this.highScores;
    }

    /**
     * Gets rank.
     *
     * @param score the score
     * @return the rank
     */
// return the rank of the current score: where will it
    // be on the list if added?
    // Rank 1 means the score will be highest on the list.
    // Rank `size` means the score will be lowest.
    // Rank > `size` means the score is too low and will not
    //      be added to the list.
    public int getRank(int score) {
        int i;
        for (i = 0; i < this.highScores.size(); i++) {
            if (score > this.highScores.get(i).getScore()) {
                break;
            }
        }
        return (i + 1);
    }

    /**
     * Clear.
     */
// Clears the table
    public void clear() {
        this.highScores.clear();
    }

    /**
     * Load.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
// Load table data from file.
    // Current table data is cleared.
    public void load(File filename) throws IOException {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(filename));
            HighScoresTable highScoresTable;
            highScoresTable = (HighScoresTable) objectInputStream.readObject();
            this.size = highScoresTable.size;
            this.highScores = highScoresTable.highScores;
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find the file: " + filename);
            HighScoresTable blankHighScoreTable = new HighScoresTable(5);
            blankHighScoreTable.save(filename);
            this.size = blankHighScoreTable.size;
            this.highScores = blankHighScoreTable.highScores;
        } catch (ClassNotFoundException e) {
            System.err.println("Unable to find a class for the object in the file: " + filename);
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed to close the file: " + filename);
            }

        }
    }

    /**
     * Save.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
// Save table data to the specified file.
    public void save(File filename) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename));
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            System.err.println("Failed to save data to file");
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed to close the file: " + filename);
            }
        }
    }

    /**
     * Load from file high scores table.
     *
     * @param filename the filename
     * @return the high scores table
     */
// Read a table from file and return it.
    // If the file does not exist, or there is a problem with
    // reading it, an empty table is returned.
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable blankTable = new HighScoresTable(6);
        try {
            if (!filename.exists()) {
                return blankTable;
            }
            blankTable.load(filename);
        } catch (IOException e) {
            System.err.println("Failed to close the file: " + filename);
            return new HighScoresTable(6);
        }
        return blankTable;
    }
}
