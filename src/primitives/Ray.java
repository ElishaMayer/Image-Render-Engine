package primitives;

import java.util.Objects;

/**
 * Ray
 */
public class Ray {

    private Point3D _point3D;
    private Vector _vector;

    /********** Constructors ***********/

    /**
     * A new Ray
     *
     * @param point3D the start point
     * @param vector  the _vector
     */
    public Ray(Point3D point3D, Vector vector) {
        _point3D = point3D;
        _vector = vector.normal();
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

    /************** Getters/Setters *******/

    /**
     * Get start point
     *
     * @return the point
     */
    public Point3D getPoint3D() {
        return _point3D;
    }

    /**
     * Get Vector
     *
     * @return the vector
     */
    public Vector getVector() {
        return _vector;
    }

    /*************** Admin *****************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Ray)) return false;
        Ray ray = (Ray) o;
        return _point3D.equals(ray._point3D) &&
                _vector.equals(ray._vector);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "P=" + _point3D +
                ", V=" + _vector +
                '}';
    }

}
