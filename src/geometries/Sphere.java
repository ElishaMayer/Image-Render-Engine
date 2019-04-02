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
     * @param radius the radius
     * @param point the middle point
     */
    public Sphere(double radius, Point3D point) {
        super(radius);
        _point = new Point3D(point);
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

    /*************** Admin *****************/

    @Override
    public String toString() {
        return "Sphere{" + super.toString() +
                "P=" + _point +
                "}";
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
