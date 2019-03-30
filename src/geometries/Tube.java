package geometries;

import primitives.Ray;

/**
 * Tube
 */
public class Tube extends RadialGeometry {

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
        this._ray = ray;
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
                "ray=" + _ray +
                '}';
    }

    /************** Operations ***************/


}
