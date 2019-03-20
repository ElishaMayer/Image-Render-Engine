package geometries;

import primitives.*;
import static primitives.Util.*;

/**
 * Radial Geometry
 */
public abstract class RadialGeometry {
    private double _radios;

    /********** Constructors ***********/

    /**
     * A new RadialGeometry
     * @param _radios the radios
     */
    public RadialGeometry(double _radios) {
        this._radios = _radios;
    }

    /**
     * A new RadialGeometry
     * @param other the other RadialGeometry
     */
    public RadialGeometry(RadialGeometry other){
        this._radios = other._radios;
    }

    /*************** Admin *****************/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RadialGeometry that = (RadialGeometry) o;
        return usubtract(that._radios, _radios) == 0.0;
    }

    @Override
    public String toString() {
        return
                "_radios=" + _radios
                ;
    }

    /************** Getters/Setters *******/

    /**
     * Get radios
     * @return
     */
    public double get_radios() {
        return _radios;
    }

    /************** Operations ***************/
    /**
     * add to radios
     * @param other to add
     */
    protected void addR(double other){
        this._radios = uadd(this._radios,other);
    }

    /**
     * subtract from radios
     * @param other to subtract
     */
    protected void subtractR(double other){
        this._radios = usubtract(this._radios,other);
    }

    /**
     * multiply radios
     * @param other to multiply
     */
    protected void multR(double other){
        this._radios = uscale(this._radios,other);
    }


}
