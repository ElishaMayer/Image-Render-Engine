package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

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
        Point3D o = _ray.getPoint3D().add(_ray.getVector().scale(t));

        return p.subtract(o).normal();
    }

    /************** Operations ***************/
}
