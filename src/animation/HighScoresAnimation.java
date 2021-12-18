package animation;

import biuoop.DrawSurface;
import game.Fill;
import highscore.HighScoresTable;
import java.awt.Color;
import java.awt.Image;

/**
 * The type High scores animation.
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable highScore;
    private Image hsImag;

    /**
     * Instantiates a new High scores animation.
     *
     * @param highScore the high score
     */
    public HighScoresAnimation(HighScoresTable highScore) {
        this.highScore = highScore;
        this.hsImag = Fill.stringToImage("Image/hsImg.jpg");

    }

    /**
     * Do one frame.
     *
     * @param d the Drawsurface to draw on
     */
   public void doOneFrame(DrawSurface d) {
        int i;
       d.drawImage(0, 0, hsImag);
       //d.setColor(new Color(93, 156, 255));
      // d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
       d.setColor(Color.black.darker());
       d.drawText(250, 100, "~High Scores~", 40);
       d.setColor(Color.orange);
       d.drawText(246, 96, "~High Scores~", 40);
        for (i = 0; i < this.highScore.getHighScores().size(); i++) {
            d.setColor(Color.black.darker());
            d.drawText(250, 200 + i * 30, i + 1 + ". " + this.highScore.getHighScores().get(i).getName(), 30);
            d.setColor(Color.orange);
            d.drawText(246, 196 + i * 30, i + 1 + ". " + this.highScore.getHighScores().get(i).getName(), 30);
            d.setColor(Color.black.darker());
            d.drawText(470, 200 + i * 30, Integer.toString(this.highScore.getHighScores().get(i).getScore()), 30);
            d.setColor(Color.orange);
            d.drawText(466, 196 + i * 30, Integer.toString(this.highScore.getHighScores().get(i).getScore()), 30);
        }
        d.setColor(Color.black);
        d.drawText(240, 570, "Press space to continue", 30);
       d.setColor(Color.orange);
       d.drawText(238, 566, "Press space to continue", 30);
    }

    /**
     * Should stop boolean.
     *
     * @return the boolean
     */
   public boolean shouldStop() {
       return false;
}

}
