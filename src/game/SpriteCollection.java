package game;

import basic.Sprite;
import biuoop.DrawSurface;

import java.util.List;
import java.util.ArrayList;

/**
 * This class maintains the sprite objects of the game by creating Sprites collection (as a List).
 */
public class SpriteCollection {
    private List<Sprite> sprites;

    /**
     * constructor.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<Sprite>();
    }

    /**
     * .
     *
     * @return blah sprites
     */
    public java.util.List getSprites() {
        return this.sprites;
    }

    /**
     * This method add a sprite to the sprites collection.
     *
     * @param sp - the sprtie to add to the collection
     */
    public void addSprite(Sprite sp) {
        this.sprites.add(sp);
    }

    /**
     * This method notifies all the sprites of the collection that a time unit has passed.
     */
    public void notifyAllTimePassed() {
        int i;
        List<Sprite> spriteList = new ArrayList<>(this.sprites);
        for (i = 0; i < spriteList.size(); i++) {
            spriteList.get(i).timePassed();
        }
    }

    /**
     * This method draws all the sprites of the collection on a given DrawSurface.
     *
     * @param d - the DrawSurface to draw sprites on
     */
    public void drawAllOn(DrawSurface d) {
        int i, size;
        size = sprites.size();
        for (i = 0; i < size; i++) {
            this.sprites.get(i).drawOn(d);
        }
    }
}