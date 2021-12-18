package game;

import java.awt.Color;
import basic.Collidable;
import basic.Sprite;
import basic.Velocity;
import basic.HitNotifier;
import basic.HitListener;
import biuoop.DrawSurface;
import geo.Point;
import geo.Rectangle;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * This class creates Block instances, and implements the Collidable and Sprite classes.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rect;
    private int hitsCount;
    private Color stroke;
    private List<HitListener> hitListeners;
    private Map<Integer, Fill> fill;

    /**
     * constructor.
     *
     * @param rect      - the rectangle that forms the block
     * @param hitsCount - number of hits of the block
     * @param stroke    - color of the block
     * @param fill      - filling
     */
    public Block(Rectangle rect, int hitsCount, Color stroke, Map<Integer, Fill> fill) {
        this.rect = rect;
        this.hitsCount = hitsCount;
        this.stroke = stroke;
        this.hitListeners = new ArrayList<HitListener>();
        this.fill = fill;
    }

    /**
     * constructor.
     *
     * @param topLeft   - top left point of a block
     * @param height    - height of the block
     * @param width     - width of the the block
     * @param hitsCount - number of hits of the block
     * @param stroke    - color of the block
     * @param fill      - sss
     */
    public Block(Point topLeft, double height, double width, int hitsCount, Color stroke, Map<Integer, Fill> fill) {
        this.rect = new Rectangle(topLeft, height, width);
        this.hitsCount = hitsCount;
        this.stroke = stroke;
        this.hitListeners = new ArrayList<HitListener>();
        this.fill = fill;
    }

    /**
     * constructor.
     *
     * @param x         - x of top left of the block
     * @param y         - y of top left of the block
     * @param height    - height of the block
     * @param width     - width of the block
     * @param hitsCount - number of hits of the block
     * @param stroke    - color of the block
     * @param fill      the fill
     */
    public Block(double x, double y, double height, double width, int hitsCount,
                 Color stroke, Map<Integer, Fill> fill) {
        this.rect = new Rectangle(x, y, height, width);
        this.hitsCount = hitsCount;
        this.stroke = stroke;
        this.hitListeners = new ArrayList<HitListener>();
        this.fill = fill;
    }

    /**
     * Instantiates a new Block.
     *
     * @param other the other
     */
    public Block(Block other) {
        this.rect = new Rectangle(other.getCollisionRectangle().getUpperLeft().getX(),
                other.getCollisionRectangle().getUpperLeft().getY(), other.getCollisionRectangle().getHeight(),
                other.getCollisionRectangle().getWidth());
        this.fill = other.fill;
        this.stroke = other.stroke;
        this.hitsCount = other.hitsCount;
        this.hitListeners = new ArrayList<HitListener>();
    }
    /**
     * This method returns the collision rectangle (that the ball collides with).
     * @return - collision rectangle
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }
    /**
     * This method change's a ball's Velocity depending on a collision point of the collidable object.
     * @param collisionPoint - the collision point of a ball and an object
     * @param currentVelocity - the current velocity of a ball
     * @param hitter - A ball hitting an object
     * @return - new ball's velocity
     */
    public Velocity hit(Point collisionPoint, Velocity currentVelocity, Ball hitter) {
        if (this.hitsCount > 0) {
            this.hitsCount--;
        }
        Velocity newVel = new Velocity(currentVelocity.getDx(), currentVelocity.getDy());
        if (this.rect.getRight().withinLine(collisionPoint) || this.rect.getLeft().withinLine(collisionPoint)) {
        newVel.setDx(-newVel.getDx());
        }
        if (this.rect.getTop().withinLine(collisionPoint) || this.rect.getBottom().withinLine(collisionPoint)) {
            newVel.setDy(-newVel.getDy());
        }
        this.notifyHit(hitter);
        return newVel;
    }
    /**
     * this method notifies a block that a time unit has passed.
     */
     public void timePassed() { }
    /**
     * This method draw's blocks on a given DrawSurface.
     * @param d - the DrawSurface to draw blocks on
     */
     public void drawOn(DrawSurface d) {
         int x1 = (int) Math.round(this.rect.getUpperLeft().getX());
         int x2 = (int) Math.round(this.rect.getUpperLeft().getY());
         int x3 = (int) Math.round(this.rect.getWidth());
         int x4 = (int) Math.round(this.rect.getHeight());
         if (this.fill.containsKey(this.hitsCount)) {
             if (this.fill.get(this.hitsCount).isCol()) {
                 d.setColor(this.fill.get(this.hitsCount).getCol());
                 d.fillRectangle(x1, x2, x3, x4);
             } else if (this.fill.get(this.hitsCount).isImg()) {
                 d.drawImage(x1, x2, this.fill.get(this.hitsCount).getImg());
             }
         } else if (this.fill.containsKey(0)) {
             if (this.fill.get(0).isCol()) {
                 d.setColor(this.fill.get(0).getCol());
                 d.fillRectangle(x1, x2, x3, x4);
             } else if (this.fill.get(0).isImg()) {
                 d.drawImage(x1, x2, this.fill.get(0).getImg());
             }
         }
         if (this.stroke != null) {
             d.setColor(this.stroke);
             d.drawRectangle(x1, x2, x3, x4);
         }
     }
    /**
     * This method adds blocks to a game instance.
     * @param game - A game instance to add the blocks to.
     */
     public void addToGame(GameLevel game) {
         game.addCollidable(this);
         game.addSprite(this);
        }

    /**
     * This method removes a block from a game.
     *
     * @param game - A game instance to remove a block from
     */
//ass 5 methods
     public void removeFromGame(GameLevel game) {
         game.removeCollidable(this);
         game.removeSprite(this);
     }

    /**
     * This method notifies all the listeners about a hitEvent that occurs.
     * @param hitter - A ball that hits an object (the ball is responsible of making hitEvents)
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * This method adds a listener to a block.
     * @param hl - A listener to add to a block object
     */
    public void addHitListener(HitListener hl) {
         this.hitListeners.add(hl);
    }

    /**
     * This method removes a listener from a block.
     * @param hl - A listener ro remove from a block object
     */
    public void removeHitListener(HitListener hl) {
         this.hitListeners.remove(hl);
    }

    /**
     * This method returns the amount of hits a block received.
     *
     * @return - Amount of hits of a block
     */
    public int getHitsCount() {
         return this.hitsCount;
    }

    /**
     * Sets hit count.
     *
     * @param hc the hc
     */
    public void setHitCount(int hc) {
        this.hitsCount = hc;
    }

}