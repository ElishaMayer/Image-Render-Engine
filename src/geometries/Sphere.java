package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Sphere
 */
public class Sphere extends RadialGeometry implements Geometry {
    private Point3D _point;

    /* ********* Constructors ***********/

    /**
     * A new Sphere
     *
     * @param radius the radius
     * @param point  the middle point
     */
    public Sphere(double radius, Point3D point) {
        super(radius);
        _point = new Point3D(point);
    }


    /* ************* Getters/Setters *******/

    /**
     * Get Point
     *
     * @return point
     */
    public Point3D getPoint() {
        return _point;
    }

    /* ************** Admin *****************/

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
     * Returns All intersections with ray
     *
     * @param ray The ray
     * @return List of intersections (Points)
     * @see Point3D#Point3D(Coordinate, Coordinate, Coordinate)
     * @see Ray#Ray(Point3D, Vector)
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        //list to return
        List<GeoPoint> list;

        //get ray point and vector
        Point3D rayP = ray.getPoint3D();
        Vector rayV = ray.getVector();

        //vector between ray start and sphere center
        Vector l;
        try {
            l = _point.subtract(rayP);
        }catch(Exception ex){
            //if Sphere is on ray start point then return p0 + r*V
            list = new ArrayList<>();
            list.add(new GeoPoint(this,rayP.add(rayV.scale(_radius))));
            return list;
        }

        //the scale for the ray in order to get parallel to the sphere center
        double tm = l.dotProduct(rayV);

        double lengthL = l.length();

        //get the distance between the ray and the sphere center
        double d2 = Util.usubtract(lengthL * lengthL, tm * tm);
        double d = Math.sqrt(d2);

        //the ray doesn't cross the sphere
        if (Util.usubtract(d, _radius) > 0.0)
            return EMPTY_LIST;

        //the ray tangent the sphere
        if (Util.usubtract(d, _radius) == 0.0) {
            list = new ArrayList<>();
            if (Util.alignZero(tm) >= 0.0)
                try {
                    list.add(new GeoPoint(this,rayP.add(rayV.scale(tm))));
                }catch(Exception ex){
                    list.add(new GeoPoint(this,rayP));
                }
            return list;
        }
        //the ray crosses the sphere is two places
        double th = Math.sqrt(Util.usubtract(_radius * _radius, d2));
        //get the distance to the two points
        double t1 = Util.usubtract(tm, th);
        double t2 = Util.uadd(tm, th);

        //return the points that are after the ray
        list = new ArrayList<>();
        if (Util.alignZero(t1) >= 0.0)
            try {
                list.add(new GeoPoint(this,rayP.add(rayV.scale(t1))));
            }catch (Exception ex){
                list.add(new GeoPoint(this,rayP));
            }
        if (Util.alignZero(t2) >= 0.0)
            try {
                list.add(new GeoPoint(this,rayP.add(rayV.scale(t2))));
            }catch (Exception ex){
                list.add(new GeoPoint(this,rayP));
            }
        return list;
    }

    /* ************* Operations ***************/
}
