package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

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
                "h=" + _height +
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

    /************** Operations ***************/
}
