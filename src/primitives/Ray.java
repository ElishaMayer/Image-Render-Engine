package primitives;


/**
 * Ray
 */
public class Ray {

    private Point3D _point3D;
    private Vector _vector;

    /* ********* Constructors ***********/

    /**
     * A new Ray
     *
     * @param p the start point
     * @param vec  the vector
     * @see Point3D#Point3D(Coordinate, Coordinate, Coordinate)
     * @see Vector#Vector(Point3D)
     */
    public Ray(Point3D p, Vector vec) {
        _point3D = new Point3D(p);
        _vector = vec.normal();
    }

    /**
     * A new Ray
     *
     * @param other Ray to copy from
     */
    public Ray(Ray other) {
        _point3D = new Point3D(other._point3D);
        _vector = new Vector(other._vector);
    }

    /* ************* Getters/Setters *******/

    /**
     * Get start point
     *
     * @return the point
     * @see Point3D#Point3D(Coordinate, Coordinate, Coordinate)
     */
    public Point3D getPoint3D() {
        return _point3D;
    }

    /**
     * Get Vector
     *
     * @return the vector
     * @see Vector#Vector(Point3D)
     */
    public Vector getVector() {
        return _vector;
    }

    /* ************** Admin *****************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray)) return false;
        Ray ray = (Ray) o;
        return _point3D.equals(ray._point3D) &&
                _vector.equals(ray._vector);
    }

    @Override
    public String toString() {
        return "Ray{" + _point3D + "," + _vector + "}";
    }
}
