package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * Cylinder
 */
public class Cylinder extends Tube implements Geometry {

    private double _height;

    /********** Constructors ***********/

    /**
     * A new Cylinder
     *
     * @param radius the radios
     * @param ray the direction vector
     * @param height the height
     */
    public Cylinder(double radius, Ray ray, double height) {
        super(radius, ray);
        this._height = height;
        if(Util.isZero(height) || height<0)
            throw new IllegalArgumentException("Height is zero or negative");
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
