package geometries;

import primitives.Ray;
import primitives.Vector;

/**
 * Cylinder
 */
public class Cylinder extends Tube {

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

    /************** Operations ***************/

}
