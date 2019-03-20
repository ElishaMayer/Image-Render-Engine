package geometries;

import primitives.Vector;

/**
 * Tube
 */
public class Tube extends RadialGeometry {

    private Vector _vector;

    /********** Constructors ***********/

    /**
     * A new Tube
     * @param _radios the radios
     * @param _vector the direction
     */
    public Tube(double _radios, Vector _vector) {
        super(_radios);
        this._vector = _vector;
    }

    /**
     * A new Tube
     * @param other other Tube
     */
    public Tube(Tube other){
        super(other.get_radios());
        this._vector=new Vector(other._vector);
    }

    /************** Getters/Setters *******/

    /**
     * Get Vector
     * @return vector
     */
    public Vector get_vector() {
        return _vector;
    }

    /*************** Admin *****************/

    @Override
    public String toString() {
        return "Tube{" + super.toString() +
                "_vector=" + _vector +
                '}';
    }

    /************** Operations ***************/


}
