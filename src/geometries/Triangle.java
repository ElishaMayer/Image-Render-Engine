package geometries;

import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Triangle
 */
public class Triangle extends Plane implements Geometry{
    private Point3D _point2;
    private Point3D _point3;

    /********** Constructors ***********/

    /**
     * A new Triangle
     *
     * @param p1 point 1
     * @param p2 point 2
     * @param p3 point 3
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1,p2,p3);
        _point2 = new Point3D(p2);
        _point3 = new Point3D(p3);
    }


    /************** Getters/Setters *******/

    /**
     * Get First point
     *
     * @return the point
     */
    public Point3D getPoint1() {
        return super._point;
    }

    /**
     * Get Second Point
     *
     * @return the point
     */
    public Point3D getPoint2() {
        return _point2;
    }

    /**
     * Get the Third point
     *
     * @return the point
     */
    public Point3D getPoint3() {
        return _point3;
    }

    /*************** Admin *****************/

    @Override
    public String toString() {
        return "Tri{" +
                "P1=" + super._point +
                ", P2=" + _point2 +
                ", P3=" + _point3 +
                '}';
    }

    /*************** Operations ***************/
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
        return super.findIntersections(ray);
    }
}
