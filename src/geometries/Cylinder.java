package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * Cylinder
 */
public class Cylinder extends Tube implements Geometry {
    private double _height;

    /********** Constructors ***********/

    /**
     * A new Cylinder
     *
     * @param radius the radius
     * @param ray the direction vector
     * @param height the height
     */
    public Cylinder(double radius, Ray ray, double height) {
        super(radius, ray);
        if(isZero(height) || height<0)
            throw new IllegalArgumentException("Height is zero or negative");
        this._height = height;
    }

    /************** Getters/Setters *******/

    /**
     * Get Height
     *
     * @return height
     */
    public double getHeight() {
        return _height;
    }

    /*************** Admin *****************/

    @Override
    public String toString() {
        return "C{" + super.toString() +
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

        if (isZero(d-_height/2)) // p is on the top base (d>0)
            return new Vector(_ray.getVector());
        if (isZero(d+_height/2)) // p is on the bottom base (d<0)
            return _ray.getVector().scale(-1);

        return super.getNormal(p);
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
        List<Point3D> list = new ArrayList<>();

        //get tube intersections
        for (Point3D p:super.findIntersections(ray)) {
            double d = Math.abs(_ray.getVector().dotProduct(p.subtract(_ray.getPoint3D())));
            if(Util.usubtract(_height/2,d)>=0.0)
                list.add(p);
        }
        try {
            //get upper plane intersections
            Point3D upperPoint = _ray.getPoint3D().add(_ray.getVector().scale(_height / 2));
            Plane upperPlane = new Plane(upperPoint, _ray.getVector());
            for (Point3D p : upperPlane.findIntersections(ray)) {
                if (Util.usubtract(_radius, upperPoint.distance(p)) >= 0)
                    list.add(p);
            }
        }catch (Exception ex){}

        try {
            //get under plane intersections
            Point3D underPoint = _ray.getPoint3D().subtract(_ray.getVector().scale(_height / 2));
            Plane underPlane = new Plane(underPoint, _ray.getVector());
            for (Point3D p : underPlane.findIntersections(ray)) {
                if (Util.usubtract(_radius, underPoint.distance(p)) >= 0)
                    list.add(p);
            }
        }catch (Exception ex){}

        return list;
    }

    /************** Operations ***************/
}
