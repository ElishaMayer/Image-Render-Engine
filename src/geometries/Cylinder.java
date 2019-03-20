package geometries;

import primitives.Vector;

/**
 * Cylinder
 */
public class Cylinder extends Tube {

    private double _height;

    /********** Constructors ***********/

    /**
     * A new Cylinder
     * @param _radios the radios
     * @param _vector the direction vector
     * @param _height the height
     */
    public Cylinder(double _radios, Vector _vector, double _height) {
        super(_radios, _vector);
        this._height = _height;
    }

    /**
     * A new Cylinder
     * @param other the other Cylinder
     */
    public Cylinder(Cylinder other){
        super(other.get_radios(),other.get_vector());
        this._height=other._height;
    }

    /************** Getters/Setters *******/

    /**
     * Get Height
     * @return height
     */
    public double get_height() {
        return _height;
    }

    /*************** Admin *****************/

    @Override
    public String toString() {
        return "Cylinder{" +super.toString()+
                "_height=" + _height +
                '}';
    }

    /************** Operations ***************/

}
