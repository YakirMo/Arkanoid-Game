package game;
import geo.Point;
import geo.Line;
import basic.Velocity;
import basic.Sprite;
import basic.CollisionInfo;
import biuoop.DrawSurface;
import java.awt.Color;

/**
 * .
 *
 * @author Yakir Moshe <YakirMoshe@gmail.com> This class creates Ball instances.
 */
public class Ball implements Sprite {
    // constructor
    private Point centerP;
    private int rad;
    private Color col;
    private Velocity veloc;
    private GameEnvironment gameEnv;
    private CollisionInfo collInfo;
    private Paddle pad;
    // 4 constructors (Point / X,Y)

    /**
     * .
     * A constructor
     *
     * @param center  - center Point of a Ball
     * @param r       - Ball's radius
     * @param color   - Ball's colors
     * @param gameEnv -
     */
    public Ball(Point center, int r, java.awt.Color color,
                GameEnvironment gameEnv) {
        this.centerP = center;
        this.rad = r;
        this.col = color;
        this.gameEnv = gameEnv;
        this.veloc = new Velocity(0, 0);
    }

    /**
     * .
     * A constructor
     *
     * @param x       - X value of a center Point of a Ball
     * @param y       - Y value of a center Point of a Ball
     * @param r       - Ball's radius
     * @param color   - Ball's color
     * @param gameEnv -
     */
    public Ball(double x, double y, int r, java.awt.Color color,
                GameEnvironment gameEnv) {
        this.centerP = new Point(x, y);
        this.rad = r;
        this.col = color;
        this.gameEnv = gameEnv;
        this.veloc = new Velocity(0, 0);
    }

    /**
     * .
     * A constructor
     *
     * @param center - center Point of a Ball
     * @param r      - Ball's radius
     * @param color  - Ball's color
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.centerP = center;
        this.rad = r;
        this.col = color;
        this.gameEnv = new GameEnvironment();
        this.veloc = new Velocity(0, 0);
    }

    /**
     * .
     * A constructor
     *
     * @param x     - X value of a center Point of a Ball
     * @param y     - Y value of a center Point of a Ball
     * @param r     - Ball's radius
     * @param color - Ball's color
     */
    public Ball(int x, int y, int r, java.awt.Color color) {
        this.centerP = new Point(x, y);
        this.rad = r;
        this.col = color;
        this.gameEnv = new GameEnvironment();
        this.veloc = new Velocity(0, 0);
    }

    /**
     * .
     * This method returns the X value of the center point of a ball
     *
     * @return - X value of center Point
     */
    public int getX() {
        return (int) this.centerP.getX();
    }

    /**
     * .
     * This method returns the Y value of the center point of a ball
     *
     * @return - Y value of center Point
     */
    public int getY() {
        return (int) this.centerP.getY();
    }

    /**
     * .
     * This method returns the radius of the ball
     *
     * @return - Radius of a Ball
     */
    public int getSize() {
        return this.rad;
    }

    /**
     * .
     * This method returns the color of a ball
     *
     * @return - Color of a Ball
     */
    public java.awt.Color getColor() {
        return this.col;
    }
    /**
     * .
     * This method draws a Ball on a given DrawSurface
     * @param surface - the DrawSurface to draw on
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(col);
        surface.fillCircle(this.getX(), this.getY(), this.rad);
    }

    /**
     * .
     * This method sets the Velocity of a Ball
     *
     * @param v - Velocity object to apply on a Ball
     */
    public void setVelocity(Velocity v) {
        this.veloc = v;
    }

    /**
     * .
     * This method make's the change on the Ball's center Point (X,Y)
     *
     * @param dx - the position change for X value (of center Point)
     * @param dy - the position change for Y value (of center Point)
     */
    public void setVelocity(double dx, double dy) {
        this.veloc = new Velocity(dx, dy);
    }

    /**
     * .
     * An accessor
     *
     * @return - Velocity of a Ball
     */
    public Velocity getVelocity() {
        return this.veloc;
    }

    /**
     * .
     * This method moves the Ball in an area, minding collidable objects and calculates future moves of the ball.
     */
    public void moveOneStep() {
        if (collInfo != null && collInfo.getCollisionObj() == pad && veloc.getDy() < 0) {
            return;
        }
        this.collInfo = null;
        //get the trajectory of the ball
        Line trajectory = traj();
        // determines if there's a collision
        this.collInfo = this.gameEnv.getClosestCollision(trajectory);
        if (this.collInfo != null) {
            this.veloc = this.collInfo.getCollisionObj().hit(this.collInfo.getCollisionP(), this.veloc, this);
            this.collisionAlignment(trajectory);
            return;
        }
        this.centerP = this.veloc.applyToPoint(this.centerP);
    }

    /**
     * This method receives the balls trajectory and changes balls center in order for a collision to occur.
     *
     * @param trajectory ball's trajectory.
     */
    public void collisionAlignment(Line trajectory) {
        double colX, colY, dis, dx, dy, x, y;
        colX = this.collInfo.getCollisionP().getX();
        colY = this.collInfo.getCollisionP().getY();
        // distance from collision point
        dx = colX - this.centerP.getX();
        dy = colY - this.centerP.getY();
        dis = this.centerP.distance(this.collInfo.getCollisionP());
        x = this.centerP.getX() + ((dis -  1.1 * this.rad)
                / dis) * dx;
        y = this.centerP.getY() + ((dis -  1.1 * this.rad)
                / dis) * dy;
        this.centerP = new Point(x, y);
    }

    /**
     * This method calculates the trajectory of a ball.
     *
     * @return - ball's trajectory
     */
    public Line traj() {
        return new Line(new Point(this.centerP.getX(), this.centerP.getY()),
                new Point(this.centerP.getX() +  this.veloc.getDx(),
                        this.centerP.getY() +  this.veloc.getDy()));
    }

    /**
     * This method return the game environment instance.
     *
     * @return - game environment instance
     */
    public GameEnvironment getGameEnv() {
        return this.gameEnv;
    }
    /**
     * This implemented method (from the Sprite class) notifies a ball that a time unit has passed.
     */
    public void timePassed() {
        this.moveOneStep();
    }
    /**
     * This implemented method (from the Sprite class) adds a ball instance to a game.
     * @param g - the game to add a ball sprite to
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * A method to remove a ball instance from a game.
     *
     * @param g - the game to remove a ball sprite from
     */
//ass5 addition
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }

}