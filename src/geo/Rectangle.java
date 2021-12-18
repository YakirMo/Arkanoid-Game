package geo;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines a Rectangle with width, height and location.
 */
public class Rectangle {
    private Line top;
    private Line right;
    private Line bottom;
    private Line left;

    /**
     * constructor.
     *
     * @param topLeft - top left point of the rectangle
     * @param height  - height of the rectangle
     * @param width   - width of the rectangle
     */
    public Rectangle(Point topLeft, double height, double width) {
        this.top = new Line(topLeft.getX(), topLeft.getY(),
                topLeft.getX() + width, topLeft.getY());
        this.right = new Line(topLeft.getX() + width, topLeft.getY(),
                topLeft.getX() + width, topLeft.getY() + height);
        this.bottom = new Line(topLeft.getX(), topLeft.getY() + height,
                topLeft.getX() + width, topLeft.getY() + height);
        this.left = new Line(topLeft.getX(), topLeft.getY(), topLeft.getX(),
                topLeft.getY() + height);
    }

    /**
     * constructor.
     *
     * @param x      - the x of the top left point of a rectangle
     * @param y      - the y of the top left point of a rectangle
     * @param height - height of the rectangle
     * @param width  - width of the rectangle
     */
    public Rectangle(double x, double y, double height, double width) {
        this.top = new Line(x, y, x + width, y);
        this.right = new Line(x + width, y, x + width, y + height);
        this.bottom = new Line(x, y + height, x + width, y + height);
        this.left = new Line(x, y, x, y + height);
    }

    /**
     * This method check's if there's intersection with one of the rectangles sides.
     *
     * @param line - a line to be checked if intersects with a rectangle
     * @return - the intersection point
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> interPoints = new ArrayList<>();
        if (line.isIntersecting(this.top)) {
            interPoints.add(line.intersectionWith(this.top));
        }
        if (line.isIntersecting(this.right)) {
            interPoints.add(line.intersectionWith(this.right));
        }
        if (line.isIntersecting(this.bottom)) {
            interPoints.add(line.intersectionWith(this.bottom));
        }
        if (line.isIntersecting(this.left)) {
            interPoints.add(line.intersectionWith(this.left));
        }
        return interPoints;
    }

    /**
     * This method returns the width of a rectangle.
     *
     * @return - width of the rectangle
     */
    public double getWidth() {
        return this.top.length();
    }

    /**
     * This method returns the height of a rectangle.
     *
     * @return - height of the rectangle
     */
    public double getHeight() {
        return this.left.length();
    }

    /**
     * This method returns the upper left point of a rectangle.
     *
     * @return - upper left point of a rectangle
     */
    public Point getUpperLeft() {
        return this.top.intersectionWith(this.left);
    }

    /**
     * This method returns the top side (line) of a rectangle.
     *
     * @return - top side of a rectangle
     */
    public Line getTop() {
        return this.top;
    }

    /**
     * This method returns the right side (line) of a rectangle.
     *
     * @return - right side of a rectangle
     */
    public Line getRight() {
        return this.right;
    }

    /**
     * This method returns the bottom side (line) of a rectangle.
     *
     * @return - bottom side of a rectangle
     */
    public Line getBottom() {
        return this.bottom;
    }

    /**
     * This method returns the left side (line) of a rectangle.
     *
     * @return - left side of a rectangle
     */
    public Line getLeft() {
        return this.left;
    }
}