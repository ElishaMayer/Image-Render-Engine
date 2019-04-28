package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.isOne;
import static primitives.Util.isZero;

/**
 * Triangle
 */
public class Triangle extends Plane implements Geometry{
    private Point3D _point2;
    private Point3D _point3;

    /* ********* Constructors ***********/

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


    /* ************* Getters/Setters *******/

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

    /* ************** Admin *****************/

    @Override
    public String toString() {
        return "Tri{" +
                "P1=" + super._point +
                ", P2=" + _point2 +
                ", P3=" + _point3 +
                '}';
    }

    /* ************** Operations ***************/
    /**
     * Returns all intersections with ray
     *
     * @param ray The ray
     * @return List of intersections (Points)
     * @see Point3D#Point3D(Coordinate, Coordinate, Coordinate)
     * @see Ray#Ray(Point3D, Vector)
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        // get the point from the plane intersection
        List<Point3D> list = super.findIntersections(ray);

        // check if there is any intersection with the plane of the triangle
        if(list.isEmpty())
            return list;

        /*
        check if the intersection point is inside the triangle

        V1 = P1−P0
        V2 = P2−P0
        V3 = P3−P0
        N1 = normalize(V1×V2)
        N2 = normalize(V2×V3)
        N3 = normalize(V3×V1)
        */
        Point3D rayPoint = ray.getPoint3D(), planePoint = list.get(0);
        Vector v1,v2,v3,n1,n2,n3;

        // check BVA - the ray starts on one of the vertexes
        if(_point.equals(rayPoint) || _point2.equals(rayPoint) || _point3.equals(rayPoint)) {
            list.clear();
            return list;
        }

        v1 = _point.subtract(rayPoint);
        v2 = _point2.subtract(rayPoint);
        v3 = _point3.subtract(rayPoint);

        // check BVA - the ray start on sides, which make it orthogonal, and then cross will make vector zero
        if (isOne(v1.normal().dotProduct(v2.normal())) || isOne(-1*v1.normal().dotProduct(v2.normal())) ||
                isOne(v2.normal().dotProduct(v3.normal())) || isOne(-1*v2.normal().dotProduct(v3.normal())) ||
                isOne(v3.normal().dotProduct(v1.normal())) || isOne(-1*v3.normal().dotProduct(v1.normal()))){
            return Intersectable.EMPTY_LIST;
        }

        n1 = v1.crossProduct(v2).normal();
        n2 = v2.crossProduct(v3).normal();
        n3 = v3.crossProduct(v1).normal();

        // check BVA - the ray point is equal to the plane point
        if(rayPoint.equals(planePoint))
                return list;

        // The point is inside if all (P−P0)∙N𝒊 have the same sign (+/-)
        // checking if got the same sign (+/-)
        // Constraint compromise: if one or more are 0.0 – no intersection
        if(Util.usubtract(planePoint.subtract(rayPoint).dotProduct(n1),0.0) > 0.0 &&
                Util.usubtract(planePoint.subtract(rayPoint).dotProduct(n2),0.0) > 0.0 &&
                Util.usubtract(planePoint.subtract(rayPoint).dotProduct(n3),0.0) > 0.0 ||
                Util.usubtract(planePoint.subtract(rayPoint).dotProduct(n1),0.0) < 0.0 &&
                        Util.usubtract(planePoint.subtract(rayPoint).dotProduct(n2),0.0) < 0.0 &&
                        Util.usubtract(planePoint.subtract(rayPoint).dotProduct(n3),0.0) < 0.0)
            return list;
        else {
            list.clear();
            return list;
        }
    }
}
