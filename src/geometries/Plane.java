package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Plane
 */
public class Plane {

    private Point3D _point;
    private Vector _vector;

    /********** Constructors ***********/

    /**
     * A new Plane
     * @param _point a point on the plane
     * @param _vector a vector in the plane
     */
    public Plane(Point3D _point, Vector _vector) {
        this._point = _point;
        this._vector = _vector;
    }

    /**
     * A new Plane
     * @param other other plane
     */
    public Plane(Plane other){
        this._point = new Point3D(other._point);
        this._vector = new Vector(other._vector);
    }

    /*************** Admin *****************/

    @Override
    public String toString() {
        return "Plane{" +
                "_point=" + _point +
                ", _vector=" + _vector +
                '}';
    }

    /************** Getters/Setters *******/

    /**
     * Get Point
     * @return point
     */
    public Point3D get_point() {
        return _point;
    }

    /**
     * Get Vector
     * @return vector
     */
    public Vector get_vector() {
        return _vector;
    }

    /************** Operations ***************/

}
