package geometries;

import primitives.Point3D;

/**
 * Sphere
 */
public class Sphere extends RadialGeometry {

    private Point3D _point;

    /********** Constructors ***********/

    /**
     * A new Sphere
     *
     * @param radius the radios
     * @param _point  the middle point
     */
    public Sphere(double radius, Point3D _point) {
        super(radius);
        this._point = _point;
    }


    /************** Getters/Setters *******/

    /**
     * Get Point
     *
     * @return point
     */
    public Point3D get_point() {
        return _point;
    }

    /*************** Admin *****************/

    @Override
    public String toString() {
        return "Sphere{" + super.toString() +
                "_point=" + _point +
                '}';
    }
    /************** Operations ***************/

}
