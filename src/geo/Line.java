package geo;

import java.util.List;

/**
 * The type Line.
 *
 * @author Yakir Moshe <YakirMoshe@gmail.com> This class creates Line instances (each defined by 2 Points).
 */
public class Line {
    private Point start;
    private Point end;
    private double free;
    /**
     * The Deviation.
     */
    static final double DEVIATION = 0.000001;

    /**
     * .
     * Constructor (based on Points)
     *
     * @param start - start Point of a Line
     * @param end   - end Point of a Line
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
        double flop = this.getSlope();
        this.free = (this.start.getY() - flop * this.start.getX());
    }

    /**
     * .
     * Constructor (based on X's and Y's for start and end Points)
     *
     * @param x1 - x of start Point of a Line
     * @param y1 - y of start Point of a Line
     * @param x2 - x of end Point of a Line
     * @param y2 - y of end Point of a Line
     */
    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * .
     * This method returns the length of a Line
     * (That by using 'distance' method of Point class)
     *
     * @return - the length of a Line
     */
    public double length() {
        return (start.distance(end));
    }

    /**
     * .
     * This method calculates the middle Point of a Line
     *
     * @return - middle Point of a line
     */
    public Point middle() {
        double midX = ((this.start.getX() + this.end.getX()) / 2);
        double midY = ((this.start.getY() + this.end.getY()) / 2);
        return new Point(midX, midY);
    }

    /**
     * .
     * This method returns the start Point of a Line
     *
     * @return - start Point of a Line
     */
    public Point start() {
        return this.start;
    }

    /**
     * .
     * This method returns the end Point of a Line
     *
     * @return - end Point of a Line
     */
    public Point end() {
        return this.end;
    }

    /**
     * .
     * This method checks whether Lines intersect
     *
     * @param other - another Line to check if current Line intersects with
     * @return - true or false whether the Lines intersect
     */
    public boolean isIntersecting(Line other) {
        if (this.intersectionWith(other) != null) {
            return true;
        }
        return false;
    }

    /**
     * .
     * This method calculates the Intersection Point between Lines
     * if such point exists
     *
     * @param other - another Line to check if current Line intersects with
     * @return - intersection point if exists, null if there's no intersection
     */
    public Point intersectionWith(Line other) {
        double slopeL1, slopeL2, interX, interY;
        //get L1 slope
        slopeL1 = this.getSlope();
        slopeL2 = other.getSlope();
        if (Math.abs(slopeL1 - slopeL2) < 0.01) {

            return null;
        }
        if (slopeL1 == Double.NEGATIVE_INFINITY) {
            interX = this.end.getX();
            interY = yInterCalc(interX, other);
        } else {
            if (slopeL2 == Double.NEGATIVE_INFINITY) {
                interX = other.end.getX();
            } else {
                interX = xInterCalc(other);
            }
            interY = yInterCalc(interX, other);
        }
        Point p = new Point(interX, interY);
        //Check that the intersection Point is not beyond the given Lines
        if (notOverTheEdge(p) && other.notOverTheEdge(p)) {
            return p;
        }
        return null;
    }

    /**
     * .
     * This method checks if Lines are equal
     *
     * @param other - another Line to check equality on
     * @return - true or false whether Lines are identical or not
     */
    public boolean equals(Line other) {
        //check equality of the Lines' Points
        if (this.start.equals(other.start) && this.end.equals(other.end)) {
            return true;
        }
        return false;
    }

    /**
     * .
     * This method checks that an intersection Point is between the edges
     * of both Lines
     *
     * @param interP - the intersection Point to check
     * @return - true or false whether the Point is between the edges or beyond
     */
    public boolean notOverTheEdge(Point interP) {
        double xMax, yMax, xMin, yMin;
        /* comparing the given point with all the start and end Points
        of both Lines */
        xMax = Math.max((int) this.start.getX(), (int) this.end.getX()) + 2;
        xMin = Math.min((int) this.start.getX(), (int) this.end.getX()) - 2;
        yMax = Math.max((int) this.start.getY(), (int) this.end.getY()) + 2;
        yMin = Math.min((int) this.start.getY(), (int) this.end.getY()) - 2;
        if ((interP.getX() >= xMin) && (interP.getY() >= yMin)
                && (interP.getX() <= xMax) && (interP.getY() <= yMax)) {
            return true;
        }
        return false;
    }

    /**
     * This method returns the closest intersection point to the start of a line.
     * if there's no intersection, it will return null
     *
     * @param rect - the rectangle to check intersection on
     * @return - the closest intersection point or null
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        Point pOne, pTwo;
        List<Point> pointsList;
        pointsList = rect.intersectionPoints(this);
        if (pointsList.size() == 0) {
            return null;
        } else if (pointsList.size() == 1) {
            return pointsList.get(0);
        } else {
            pOne = pointsList.get(0);
            pTwo = pointsList.get(1);
            if (this.start.distance(pOne) > this.start.distance(pTwo)) {
                return pTwo;
            } else {
                return pOne;
            }
        }
    }

    /**
     * This method checks whether a point is on a line or not.
     *
     * @param interP - the point to check if it's on the line
     * @return - true if on line, false otherwise
     */
    public boolean onLine(Point interP) {
        double xMax, yMax, xMin, yMin;
        if (this.start.getX() > this.end.getX()) {
            xMax = this.start.getX();
            xMin = (this.end.getX());
        } else {
            xMax = this.end.getX();
            xMin = this.start.getX();
        }
        if (this.start.getY() > this.end.getY()) {
            yMax = this.start.getY();
            yMin = (this.end.getY());
        } else {
            yMax = this.end.getY();
            yMin = (this.start.getY());
        }
        if ((Math.abs(interP.getX()) >= Math.abs(xMin - DEVIATION))
                && (Math.abs(interP.getY()) >= Math.abs(yMin - DEVIATION))
                && (Math.abs(interP.getX()) <= Math.abs(xMax + DEVIATION))
                && Math.abs(interP.getY()) <= Math.abs(yMax + DEVIATION)) {
            return true;
        }
        return false;
    }

    /**
     * This method checks if a point is on a line or not.
     *
     * @param p - the point to check whether is on a line or not
     * @return - true if a point is on the line, false otherwise
     */
    public boolean withinLine(Point p) {
        if (p.getX() >= (Math.min(this.start.getX(), this.end.getX()) - 1)
                && (p.getX() <= (Math.max(this.start.getX(), this.end.getX() + 1)))) {
            if (p.getY() >= (Math.min(this.start.getY(), this.end.getY()) - 1)
                    && (p.getY() <= (Math.max(this.start.getY(), this.end.getY() + 1)))) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method calculates the Slope of a line.
     *
     * @return - the calculated slope of a line
     */
    public double getSlope() {
        if (Math.abs(this.end.getX() - this.start.getX()) < 0.01) {
            //only a double can be returned
            return Double.NEGATIVE_INFINITY;
        }
        return ((this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX()));
    }

    /**
     * This method calculates the X of an intersection point.
     *
     * @param l - the line to check intersection with
     * @return - the X of the intersection point
     */
    private double xInterCalc(Line l) {
        double slopeL1, slopeL2, interX;
        slopeL1 = this.getSlope();
        slopeL2 = l.getSlope();
        interX = ((slopeL1 * this.end.getX()) - this.end.getY()
                - slopeL2 * l.end.getX() + l.end.getY()) / (slopeL1 - slopeL2);
        return interX;
    }

    /**
     * This method calculates the Y of an intersection point.
     *
     * @param l - the line to check intersection with
     * @param x - the X of the intersection point
     * @return - the Y of the intersection point
     */
    private double yInterCalc(double x, Line l) {
        double slopeL1, slopeL2, interY;
        slopeL1 = this.getSlope();
        slopeL2 = l.getSlope();
        if (slopeL1 == Double.NEGATIVE_INFINITY) {
            interY = slopeL2 * x - slopeL2 * l.end.getX() + l.end.getY();
            return interY;
        }
        interY = slopeL1 * x - slopeL1 * this.end.getX() + this.end.getY();
        return interY;
    }

    /**
     * This method check's whether a line is vertical.
     *
     * @return - true if vertical, false otherwise
     */
    public boolean isVertical() {
        return (this.start.getX() == this.end.getX());
    }
}