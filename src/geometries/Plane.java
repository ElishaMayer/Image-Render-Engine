package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Plane
 */
public class Plane implements Geometry{

    protected Point3D _point;
    private Vector _vector;

    /********** Constructors ***********/

    /**
     * A new Plane
     *
     * @param p  a point on the plane
     * @param vec a vector in the plane
     */
    public Plane(Point3D p, Vector vec) {
        _point = new Point3D(p);
        _vector = vec.normal();
    }

    /**
     * A new Plane
     * @param p1 Point 1
     * @param p2 Point 2
     * @param p3 Point 3
     */
    public Plane(Point3D p1 , Point3D p2 ,Point3D p3){
        _point = new Point3D(p1);
        Vector v1 = p2.subtract(p3);
        Vector v2 = p2.subtract(p1);
        _vector = v1.crossProduct(v2).normal();
    }

    /*************** Admin *****************/

    @Override
    public String toString() {
        return "Plane{" + _point + ", " + _vector + "}";
    }

    /************** Getters/Setters *******/

    /**
     * Get Point
     *
     * @return point
     */
    public Point3D getPoint() {
        return _point;
    }

    /**
     * Get Vector
     *
     * @return vector
     */
    public Vector getVector() {
        return _vector;
    }

    /**
     * Get the normal from the point in the shape
     *
     * @param p the point
     * @return the normal
     */
    @Override
    public Vector getNormal(Point3D p) {
        return new Vector(_vector);
    }

    /************** Operations ***************/

}
