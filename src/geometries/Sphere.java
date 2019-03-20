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
     * @param _radios the radios
     * @param _point the middle point
     */
    public Sphere(double _radios, Point3D _point) {
        super(_radios);
        this._point = _point;
    }

    /**
     * A new Sphere
     * @param other other Sphere
     */
    public Sphere(Sphere other){
        super(other.get_radios());
        this._point = new Point3D(other._point);
    }

    /************** Getters/Setters *******/

    /**
     * Get Point
     * @return point
     */
    public Point3D get_point() {
        return _point;
    }

    /*************** Admin *****************/

    @Override
    public String toString() {
        return "Sphere{" + super.toString()+
                "_point=" + _point +
                '}';
    }
    /************** Operations ***************/

}
