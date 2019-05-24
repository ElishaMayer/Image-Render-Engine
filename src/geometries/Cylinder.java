package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * Cylinder
 */
public class Cylinder extends Tube   {
    private double _height;

    /* ********* Constructors ***********/

    /**
     * A new Cylinder
     *
     * @param radius the radius
     * @param ray    the direction vector and middle point
     * @param height the height
     */
    public Cylinder(double radius, Ray ray, double height) {
        super(radius, ray);
        if (isZero(height) || height < 0)
            throw new IllegalArgumentException("Height is zero or negative");
        this._height = height;
    }

    /**
     * A new Cylinder
     *
     * @param radius the radius
     * @param ray    the direction vector and middle point
     * @param height the height
     * @param material material
     * @param emission emission
     */
    public Cylinder(double radius, Ray ray, double height,Material material,Color emission) {
        super(radius, ray,material,emission);
        if (isZero(height) || height < 0)
            throw new IllegalArgumentException("Height is zero or negative");
        this._height = height;
    }

    /* ************* Getters/Setters *******/

    /**
     * Get Height
     *
     * @return height
     */
    public double getHeight() {
        return _height;
    }

    /* ************** Admin *****************/

    @Override
    public String toString() {
        return "Cylinder{" + super.toString() +
                " ,h=" + _height +
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
        double d = _ray.getVector().dotProduct(p.subtract(_ray.getPoint3D()));

        if (isZero(d - _height / 2)) // p is on the top base (d>0)
            return new Vector(_ray.getVector());
        if (isZero(d + _height / 2)) // p is on the bottom base (d<0)
            return _ray.getVector().scale(-1);

        return super.getNormal(p);
    }

    /**
     *  Return All intersections with ray
     *
     * @param ray The ray
     * @return List of intersections (Points)
     * @see Point3D#Point3D(Coordinate, Coordinate, Coordinate)
     * @see Ray#Ray(Point3D, Vector)
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        List<GeoPoint> list = new ArrayList<>();
        Point3D rayP = _ray.getPoint3D();
        Vector rayV = _ray.getVector();

        //get tube intersections
        for (GeoPoint p : super.findIntersections(ray)) {
            double d = Math.abs(rayV.dotProduct(p.point.subtract(rayP)));
            //if point is in the range
            if (Util.usubtract(_height / 2, d) >= 0.0)
                list.add(new GeoPoint(this,p.point));
        }

        //get upper plane intersections
        Point3D upperPoint = rayP.add(rayV.scale(_height / 2));
        Plane upperPlane = new Plane(upperPoint, rayV);
        for (GeoPoint p : upperPlane.findIntersections(ray)) {
            //if point is in the range
            if (Util.usubtract(_radius, upperPoint.distance(p.point)) >= 0)
                list.add(new GeoPoint(this,p.point));
        }

        //get under plane intersections
        Point3D underPoint = rayP.subtract(rayV.scale(_height / 2));
        Plane underPlane = new Plane(underPoint, rayV);
        for (GeoPoint p : underPlane.findIntersections(ray)) {
            //if point is in the range
            if (Util.usubtract(_radius, underPoint.distance(p.point)) >= 0)
                list.add(new GeoPoint(this,p.point));
        }

        return list;
    }

    /* ************* Operations ***************/
}
