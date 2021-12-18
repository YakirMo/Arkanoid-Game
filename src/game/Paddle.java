package game;

import geo.Rectangle;
import geo.Point;
import basic.Sprite;
import basic.Collidable;
import basic.Velocity;

import java.awt.Color;

import biuoop.KeyboardSensor;
import biuoop.DrawSurface;

/**
 * This class creates a paddle object and implements the Sprite and Collidable interfaces.
 */
public class Paddle implements Sprite, Collidable {
    private Rectangle rect;
    private Color col;
    private biuoop.KeyboardSensor key;
    private int borderBlock;
    private int horiz = 800;
    private int vert = 600;

    /**
     * constructor.
     *
     * @param rect        - a rectangle the paddle if formed of
     * @param borderBlock - size of the border
     * @param col         - color of the paddle
     * @param ks          - the keyboardSensor
     */
    public Paddle(Rectangle rect, int borderBlock, Color col, KeyboardSensor ks) {
        this.rect = rect;
        this.borderBlock = borderBlock;
        this.col = col;
        this.key = ks;
    }

    /**
     * constructor.
     *
     * @param topLeft     - top left point of the paddle
     * @param height      - height of the paddle
     * @param width       - width of the paddle
     * @param borderBlock - size of the border
     * @param col         - color of the paddle
     * @param ks          - the keyboardSensor
     */
    public Paddle(Point topLeft, double height, double width, int borderBlock, Color col, KeyboardSensor ks) {
        this.rect = new Rectangle(topLeft, height, width);
        this.borderBlock = borderBlock;
        this.col = col;
        this.key = ks;
    }

    /**
     * constructor.
     *
     * @param x           - x of top left point of the paddle
     * @param y           - y of top left point of the paddle
     * @param height      - height of the paddle
     * @param width       - width of the paddle
     * @param borderBlock - size of the border
     * @param col         - color of the paddle
     * @param ks          - the keyboardSensor
     */
    public Paddle(double x, double y, double height, double width, int borderBlock, Color col, KeyboardSensor ks) {
        this.rect = new Rectangle(x, y, height, width);
        this.borderBlock = borderBlock;
        this.col = col;
        this.key = ks;
    }

    /**
     * This method moves the paddle to the right.
     */
    public void moveRight() {
        if ((key.isPressed(KeyboardSensor.RIGHT_KEY))
                && (this.rect.getUpperLeft().getX() + 6
                + this.rect.getWidth() <= horiz - borderBlock)) {
            Point topLeft = this.rect.getUpperLeft();
            this.rect = new Rectangle(topLeft.getX() + 6, topLeft.getY(),
                    this.rect.getHeight(), this.rect.getWidth());
        }
    }

    /**
     * This method moves the paddle to the left.
     */
    public void moveLeft() {
        if ((key.isPressed(KeyboardSensor.LEFT_KEY))
                && ((this.rect.getUpperLeft().getX() - 6) >= borderBlock)) {
            Point topLeft = this.rect.getUpperLeft();
            this.rect = new Rectangle(topLeft.getX() - 6, topLeft.getY(),
                    this.rect.getHeight(), this.rect.getWidth());
        }
    }

    /**
     * This method notifies the paddle that a time unit has passed.
     */
    public void timePassed() {
        this.moveRight();
        this.moveLeft();
    }

    /**
     * This method draws the paddle on a given DrawSurface.
     *
     * @param d - the DrawSurface to draw paddle on
     */
    public void drawOn(DrawSurface d) {
        Point topLeft = this.rect.getUpperLeft();
        d.setColor(this.col);
        d.fillRectangle((int) topLeft.getX(), (int) topLeft.getY(),
                (int) this.rect.getWidth(), (int) this.rect.getHeight());
        d.setColor(Color.black);
        d.drawRectangle((int) topLeft.getX(), (int) topLeft.getY(),
                (int) this.rect.getWidth(), (int) this.rect.getHeight());
    }

    /**
     * This method returns the collision rectangle which means the paddle.
     *
     * @return - the paddle
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * This method determines which angle and velocity the ball will get after hitting the paddle.
     * thus by dividing the paddle to 5 parts, when each part has a different angle
     *
     * @param collisionPoint  - collision point with another object
     * @param currentVelocity - current velocity of the object
     * @param hitter          - blah
     * @return - new angle and velocity to the ball
     */
    public Velocity hit(Point collisionPoint, Velocity currentVelocity, Ball hitter) {
        double regions, topLeftX, mostLeft, left, mid, right, mostRight;
        regions = (this.rect.getWidth() / 5);
        topLeftX = this.rect.getUpperLeft().getX();
        mostRight = topLeftX + 5 * regions;
        right = topLeftX + 4 * regions;
        mid = topLeftX + 3 * regions;
        left = topLeftX + 2 * regions;
        mostLeft = topLeftX + regions;

        if ((collisionPoint.getX() >= right) && (collisionPoint.getX() < mostRight)) {
            return Velocity.fromAngleAndSpeed(60, currentVelocity.getSpeed());
        }
        if ((collisionPoint.getX() >= mid) && (collisionPoint.getX() < right)) {
            return Velocity.fromAngleAndSpeed(30, currentVelocity.getSpeed());
        }
        if ((collisionPoint.getX() >= left) && (collisionPoint.getX() < mid)) {
            return new Velocity(currentVelocity.getDx(), (-currentVelocity.getDy()));
        }
        if ((collisionPoint.getX() >= mostLeft) && (collisionPoint.getX() < left)) {
            return Velocity.fromAngleAndSpeed(330, currentVelocity.getSpeed());
        }
        if ((collisionPoint.getX() >= topLeftX) && (collisionPoint.getX() < mostLeft)) {
            return Velocity.fromAngleAndSpeed(300, currentVelocity.getSpeed());
        }
        if (this.rect.getLeft().withinLine(collisionPoint)) {
            return Velocity.fromAngleAndSpeed(290, currentVelocity.getSpeed());
        }
        if (this.rect.getRight().withinLine(collisionPoint)) {
            return Velocity.fromAngleAndSpeed(80, currentVelocity.getSpeed());
        }
        return currentVelocity;
    }

    /**
     * This method adds the paddle to a game (by adding to collidables and sprites).
     *
     * @param g - the game to add the paddle to
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
}