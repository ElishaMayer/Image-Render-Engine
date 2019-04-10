package geometries;

import primitives.*;

import java.util.ArrayList;
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
                " ,P=" + _point +
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
     * All intersections with ray
     *
     * @param ray The ray
     * @return List of intersections
     * @see Point3D#Point3D(Coordinate, Coordinate, Coordinate)
     * @see Ray#Ray(Point3D, Vector)
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        //list to return
        List<Point3D> list = new ArrayList<>();

        //if Sphere is on (0,0,0)
        if(_point.equals(ray.getPoint3D())){
            list.add(ray.getPoint3D().add(ray.getVector().scale(_radius)));
            return list;
        }

        //vector between ray start and sphere center
        Vector L = _point.subtract(ray.getPoint3D());
        //the scale for the ray in order to get parallel to the sphere center
        double tm = L.dotProduct(ray.getVector());

        double lengthL = L.length();
        //get the distance between the ray and the sphere center
        double d = Math.sqrt(Util.usubtract( lengthL*lengthL,tm*tm));

        //the ray doesn't cross the sphere
        if(Util.usubtract(d,_radius) > 0.0)
            return list;
        //the ray tangent the sphere
        if(Util.usubtract(d,_radius) == 0.0) {
            if(Util.isZero(tm))
                list.add(ray.getPoint3D());
            else if(Util.usubtract(tm,0.0)>0.0)
                list.add(ray.getPoint3D().add(ray.getVector().scale(tm)));
            return list;
        }
        //the ray crosses the sphere is two places
        double th = Math.sqrt(Util.usubtract(_radius*_radius,d*d));
        double t1 = Util.usubtract(tm,th);
        double t2 = Util.uadd(tm,th);
        if(Util.usubtract(t1,0.0) > 0.0)
            list.add(ray.getPoint3D().add(ray.getVector().scale(t1)));
        if(Util.usubtract(t1,0.0) == 0.0)
            list.add(ray.getPoint3D());
        if(Util.usubtract(t2,0.0) > 0.0)
            list.add(ray.getPoint3D().add(ray.getVector().scale(t2)));
        if(Util.usubtract(t2,0.0) == 0.0)
            list.add(ray.getPoint3D());

        return list;
    }

    /************** Operations ***************/
}
