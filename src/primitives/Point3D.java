package primitives;

/**
 * Point in 3D
 */
public class Point3D {
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
    public Coordinate get_x() {
        return _x;
    }

    /**
     * Get Y coordinate
     *
     * @return the value
     */
    public Coordinate get_y() {
        return _y;
    }

    /**
     * Get Z coordinate
     *
     * @return the value
     */
    public Coordinate get_z() {
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
                this._x.add(vec.get_point3D().get_x()),
                this._y.add(vec.get_point3D().get_y()),
                this._z.add(vec.get_point3D().get_z())
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
                this._x.subtract(vec.get_point3D().get_x()),
                this._y.subtract(vec.get_point3D().get_y()),
                this._z.subtract(vec.get_point3D().get_z())
        );
    }

    /**
     * Subtract two points
     * @param other the second point ( start point )
     * @return a vector from the other point to the current point
     */
    public Vector subtract(Point3D other){
        return new Vector(
                new Point3D(
                        this._x.subtract(other.get_x()),
                        this._y.subtract(other.get_y()),
                        this._z.subtract(other.get_z())

                        )
        );
    }

    /**
     * get the distance between the two points in the power of 2
     * @param other the second point
     * @return the distance in the power of 2
     */
    public double distance2(Point3D other){
        return
                (this._x.subtract(other.get_x())).multiply(this._x.subtract(other.get_x())).add(
                        (this._y.subtract(other.get_y())).multiply(this._y.subtract(other.get_y())) ).add(
                            (this._z.subtract(other.get_z())).multiply(this._z.subtract(other.get_z())) ).get() ;
    }

    /**
     * get the distance between the two points
     * @param other the second point
     * @return the distance
     */
    public double distance(Point3D other){
        return  Math.sqrt(distance2(other));
    }
}
