package geometries;

import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Tube
 */
public class Tube extends RadialGeometry implements Geometry{
    protected Ray _ray;

    /********** Constructors ***********/

    /**
     * A new Tube
     *
     * @param radius the radius
     * @param ray the direction
     */
    public Tube(double radius, Ray ray) {
        super(radius);
        _ray = new Ray(ray);
    }


    /************** Getters/Setters *******/

    /**
     * Get Vector
     *
     * @return vector
     */
    public Ray getRay() {
        return _ray;
    }

    /*************** Admin *****************/

    @Override
    public String toString() {
        return "Tube{" + super.toString() +
                ", " + _ray +
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
        double t = _ray.getVector().dotProduct(p.subtract(_ray.getPoint3D()));
        if(t!=0){
            Point3D o = _ray.getPoint3D().add(_ray.getVector().scale(t));
            return p.subtract(o).normal();
        }

        return p.subtract(_ray.getPoint3D()).normal();
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
