package levels;

import basic.Sprite;
import biuoop.DrawSurface;
import game.Fill;
import game.GameLevel;
import geo.Point;
import geo.Rectangle;

/**
 * The type Background.
 */
public class Background implements Sprite {
    private Fill background;

    /**
     * Instantiates a new Background.
     *
     * @param background the background
     */
    public Background(Fill background) {
        this.background = background;
    }
    @Override
    public void drawOn(DrawSurface d) {
        if (background.isImg()) {
            d.drawImage(0, 0, background.getImg());
        } else {
            Rectangle rect = new Rectangle(new Point(20, 20), 580, 780);
            int x1 = (int) rect.getUpperLeft().getX();
            int x2 = (int) rect.getUpperLeft().getY();
            int x3 = (int) rect.getWidth();
            int x4 = (int) rect.getHeight();
            d.setColor(this.background.getCol());
            d.fillRectangle(x1, x2, x3, x4);
        }
    }
    @Override
    public void timePassed() { }
    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
