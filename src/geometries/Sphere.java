package geometries;

import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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

    /**
     * All intections with ray
     *
     * @param ray The ray
     * @return List of intersactions
     * @see Point3D#Point3D(Coordinate, Coordinate, Coordinate)
     * @see Ray#Ray(Point3D, Vector)
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }

    /************** Operations ***************/
}
