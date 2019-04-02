package primitives;

/**
 * Point in 3D
 */
public class Point3D {
    public static final Point3D ZERO = new Point3D(0,0,0);

    private Coordinate _x;
    private Coordinate _y;
    private Coordinate _z;

    /********** Constructors ***********/
    /**
     * A new Point in 3D
     *
     * @param _x X coordinate
     * @param _y Y coordinate
     * @param _z Z coordinate
     */
    public Point3D(Coordinate _x, Coordinate _y, Coordinate _z) {
        this._x = _x;
        this._y = _y;
        this._z = _z;
    }

    /**
     * A new Point3D
     *
     * @param _x
     * @param _y
     * @param _z
     */
    public Point3D(double _x, double _y, double _z) {
        this._x = new Coordinate(_x);
        this._y = new Coordinate(_y);
        this._z = new Coordinate(_z);
    }

    /**
     * Copy Point
     *
     * @param other
     */
    public Point3D(Point3D other) {
        this._x = new Coordinate(other._x);
        this._y = new Coordinate(other._y);
        this._z = new Coordinate(other._z);
    }

    /************** Getters/Setters *******/
    /**
     * Get X coordinate
     *
     * @return the value
     */
    public Coordinate getX() {
        return _x;
    }

    /**
     * Get Y coordinate
     *
     * @return the value
     */
    public Coordinate getY() {
        return _y;
    }

    /**
     * Get Z coordinate
     *
     * @return the value
     */
    public Coordinate getZ() {
        return _z;
    }

    /*************** Admin *****************/
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point3D)) return false;
        Point3D other = (Point3D) obj;
        return this._z.equals(other._z) &&
                this._y.equals(other._y) &&
                this._x.equals(other._x);
    }

    @Override
    public String toString() {
        return "( " + this._x + ", " + this._y + ", " + this._z + " )";
    }

    /************** Operations ***************/
    /**
     * Add a vector to a point in 3D
     *
     * @param vec the vector
     * @return a new point
     */
    public Point3D add(Vector vec) {
        return new Point3D(
                this._x.add(vec.getPoint3D().getX()),
                this._y.add(vec.getPoint3D().getY()),
                this._z.add(vec.getPoint3D().getZ())
        );
    }

    /**
     * Subtract a vector from a point in 3D
     *
     * @param vec the vector
     * @return a new point
     */
    public Point3D subtract(Vector vec) {
        return new Point3D(
                this._x.subtract(vec.getPoint3D().getX()),
                this._y.subtract(vec.getPoint3D().getY()),
                this._z.subtract(vec.getPoint3D().getZ())
        );
    }

    /**
     * Subtract two points
     *
     * @param other the second point ( start point )
     * @return a vector from the other point to the current point
     */
    public Vector subtract(Point3D other) {
        return new Vector(
                new Point3D(
                        this._x.subtract(other.getX()),
                        this._y.subtract(other.getY()),
                        this._z.subtract(other.getZ())

                )
        );
    }

    /**
     * get the distance between the two points in the power of 2
     *
     * @param other the second point
     * @return the distance in the power of 2
     */
    public double distance2(Point3D other) {
        return
                (this._x.subtract(other.getX())).multiply(this._x.subtract(other.getX())).add(
                        (this._y.subtract(other.getY())).multiply(this._y.subtract(other.getY()))).add(
                        (this._z.subtract(other.getZ())).multiply(this._z.subtract(other.getZ()))).get();
    }

    /**
     * get the distance between the two points
     *
     * @param other the second point
     * @return the distance
     */
    public double distance(Point3D other) {
        return Math.sqrt(distance2(other));
    }
}
