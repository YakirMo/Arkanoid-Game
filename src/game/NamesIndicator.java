package game;

import basic.Sprite;
import biuoop.DrawSurface;
import geo.Rectangle;
import java.awt.Color;

/**
 * The type Names indicator.
 */
public class NamesIndicator implements Sprite {
    private String lvName;
    private Rectangle rect;

    /**
     * Instantiates a new Name indicator.
     *
     * @param lvName the lv name
     */
    public NamesIndicator(String lvName) {
        this.lvName = lvName;
        this.rect = new Rectangle(525, 0, 15, 300);
    }

    /**
     * This method draws a name indicator to the screen.
     *
     * @param d - the DrawSurface to draw on
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.gray.brighter());
        d.fillRectangle((int) this.rect.getUpperLeft().getX(),
                (int) this.rect.getUpperLeft().getY(),
                (int) this.rect.getWidth(),
                (int) this.rect.getHeight());
        d.setColor(Color.black);
        String name = "Level Name: " + (this.lvName);
        d.drawText((int) (this.rect.getUpperLeft().getX() + this.rect.getWidth() / 2 - 150),
                (int) (this.rect.getUpperLeft().getY() + this.rect.getHeight() / 2 + 15), name, 15);
    }

    /**
     * Unused method.
     */
    public void timePassed() {
    }

    /**
     * This method add a name indicator to the game.
     *
     * @param g - the game to add a name indicator to
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
