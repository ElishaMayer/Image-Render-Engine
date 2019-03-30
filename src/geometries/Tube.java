package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Tube
 */
public class Tube extends RadialGeometry implements Geometry{

    private Ray _ray;

    /********** Constructors ***********/

    /**
     * A new Tube
     *
     * @param radius the radios
     * @param ray the direction
     */
    public Tube(double radius, Ray ray) {
        super(radius);
        this._ray = new Ray(ray);
    }


    /************** Getters/Setters *******/

    /**
     * Get Vector
     *
     * @return vector
     */
    public Ray getRay()
    {
        return _ray;
    }

    /*************** Admin *****************/

    @Override
    public String toString() {
        return "Tube{" + super.toString() +
                "ray=" + _ray +
                '}';
    }

    /**
     * Get the normal from the point in the shape
     *
     * @param p the point
     * @return the normal
     */
    @Override
    public Vector getNormal(Point3D p) {
        return null;
    }

    /************** Operations ***************/


}
