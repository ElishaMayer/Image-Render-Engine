package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Sphere
 */
public class Sphere extends RadialGeometry implements Geometry{

    private Point3D _point;

    /********** Constructors ***********/

    /**
     * A new Sphere
     *
     * @param radius the radios
     * @param _point  the middle point
     */
    public Sphere(double radius, Point3D _point) {
        super(radius);
        this._point = new Point3D(_point);
    }


    /************** Getters/Setters *******/

    /**
     * Get Point
     *
     * @return point
     */
    public Point3D get_point() {

        return _point;
    }

    /*************** Admin *****************/

    @Override
    public String toString() {
        return "Sphere{" + super.toString() +
                "_point=" + _point +
                '}';
    }

    /**
     * Get the normal from the point in the shape
     *
     * @param p the point
     * @return the normal
     */
    @Override
    public Vector getNormal(Point3D p) {
        return p.subtract(_point).normal();
    }
    /************** Operations ***************/

}
