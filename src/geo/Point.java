package geo;

/**
 * The type Point.
 *
 * @author Yakir Moshe <YakirMoshe@gmail.com> This class creates Point instances.
 */
public class Point {
    private double x;
    private double y;

    /**
     * constructor.
     *
     * @param x - the x value of a Point
     * @param y - the y value of a Point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * .
     * This method returns the distance between current Point & another Point.
     *
     * @param other - another Point to check distance on
     * @return - the distance between the two points
     */
    public double distance(Point other) {
        double dist;
        //checks if the Points are the same Point
        if (other.equals(this)) {
            return 0;
        }
        //the formula to get the distance between the points
        dist = ((this.x - other.x) * (this.x - other.x))
                + ((this.y - other.y) * (this.y - other.y));
        dist = Math.sqrt(dist);
        return dist;
    }

    /**
     * .
     * This method checks equality of two Points (x,y values)
     *
     * @param other - another Point to check equality on
     * @return - true or false whether the Points are equal or not
     */
    public boolean equals(Point other) {
        if ((Math.abs(this.x - other.x) < 0.01)
                && (Math.abs(this.y - other.y) < 0.01)) {
            return true;
        }
        return false;
    }

    /**
     * .
     * This method returns the X value of a point
     *
     * @return - X value of a Point
     */
// Return the x and y values of this point
    public double getX() {
        return this.x;
    }

    /**
     * .
     * This method returns the Y value of a point
     *
     * @return - Y value of a Point
     */
    public double getY() {
        return this.y;
    }
}
