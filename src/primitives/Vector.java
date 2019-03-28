package primitives;

/**
 * Vector
 */
public class Vector {
    private Point3D _point3D;

    /********** Constructors ***********/
    /**
     * A new vector
     *
     * @param _point3D the end of the vector
     */
    public Vector(Point3D _point3D) {
        if (Point3D.ZERO.equals(_point3D)) {
            throw new IllegalArgumentException("Vector Zero");
        }
        this._point3D = _point3D;
    }

    /**
     * A new Vector
     *
     * @param _x
     * @param _y
     * @param _z
     */
    public Vector(double _x, double _y, double _z) {
        Point3D _point3D = new Point3D(new Coordinate(_x), new Coordinate(_y), new Coordinate(_z));
        if (_point3D.getX().equals(Coordinate.ZERO) && _point3D.get_y().equals(Coordinate.ZERO) && _point3D.get_z().equals(Coordinate.ZERO)) {
            throw new IllegalArgumentException("Vector Zero");
        }
        this._point3D = _point3D;
    }

    /**
     * Copy vector
     *
     * @param other
     */
    public Vector(Vector other) {
        this._point3D = new Point3D(other._point3D);
    }

    /************** Getters/Setters *******/
    /**
     * Get point3D
     *
     * @return the value
     */
    public Point3D get_point3D() {
        return _point3D;
    }

    /*************** Admin *****************/
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Vector)) return false;
        Vector other = (Vector) obj;
        return this._point3D.equals(other._point3D);
    }

    @Override
    public String toString() {
        return "v: " + this._point3D;
    }

    /************** Operations ***************/
    /**
     * Add vector to the vector
     *
     * @param other the vector to add
     * @return a new vector
     */
    public Vector add(Vector other) {
        return new Vector(this._point3D.add(other));
    }

    /**
     * Subtract vector from the vector
     *
     * @param other the vector to sub
     * @return a new vector
     */
    public Vector subtract(Vector other) {
        return new Vector(this._point3D.subtract(other));
    }

    /**
     * Scale the vector
     *
     * @param num value to scale
     * @return a new vector
     */
    public Vector scale(double num) {
        return new Vector(new Point3D(
                this._point3D.getX().scale(num),
                this._point3D.get_y().scale(num),
                this._point3D.get_z().scale(num))
        );
    }

    /**
     * dot-product with the vector
     *
     * @param other the vector to dot-product with
     * @return the value
     */
    public double dot_product(Vector other) {
        return (this._point3D.getX().multiply(other.get_point3D().getX())).get() +
                (this._point3D.get_y().multiply(other.get_point3D().get_y())).get() +
                (this._point3D.get_z().multiply(other.get_point3D().get_z())).get();

    }

    /**
     * cross-product with the vector
     *
     * @param other the vector to cross-product with
     * @return a new vector
     */
    public Vector cross_product(Vector other) {
        return new Vector(new Point3D(
                (this._point3D.get_y().multiply(other.get_point3D().get_z())).subtract(
                        this._point3D.get_z().multiply(other.get_point3D().get_y())),
                (this._point3D.get_z().multiply(other.get_point3D().getX())).subtract(
                        this._point3D.getX().multiply(other.get_point3D().get_z())),
                (this._point3D.getX().multiply(other.get_point3D().get_y())).subtract(
                        this._point3D.get_y().multiply(other.get_point3D().getX())))
        );
    }

    /**
     * the length of the vector
     *
     * @return the value
     */
    public double length() {
        return Math.sqrt((this._point3D.getX().multiply(this._point3D.getX())).get() +
                (this._point3D.get_y().multiply(this._point3D.get_y())).get() +
                (this._point3D.get_z().multiply(this._point3D.get_z())).get()
        );
    }

    /**
     * the length in the pow 2
     *
     * @return the value
     */
    public double length2() {
        return (this._point3D.getX().multiply(this._point3D.getX())).get() +
                (this._point3D.get_y().multiply(this._point3D.get_y())).get() +
                (this._point3D.get_z().multiply(this._point3D.get_z())).get()
                ;
    }

    /**
     * get the normalization of the vector
     *
     * @return a new vector
     */
    public Vector normal() {
        return new Vector(new Point3D(
                this._point3D.getX().scale(1 / this.length()),
                this._point3D.get_y().scale(1 / this.length()),
                this._point3D.get_z().scale(1 / this.length()))
        );
    }
}
