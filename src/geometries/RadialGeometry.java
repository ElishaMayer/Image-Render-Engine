package geometries;

import primitives.*;

import static primitives.Util.*;

/**
 * Radial Geometry
 */
public abstract class RadialGeometry {
    protected double _radius;

    /********** Constructors ***********/

    /**
     * A new RadialGeometry
     *
     * @param radius the radios
     */
    public RadialGeometry(double radius) {
        if(Util.isZero(radius) || radius < 0)
            throw new IllegalArgumentException("Zero or negative radius ");
        this._radius = radius;
    }


    /*************** Admin *****************/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RadialGeometry other = (RadialGeometry) o;
        return usubtract(other._radius, _radius) == 0.0;
    }

    @Override
    public String toString() {
        return
                "_radios=" + _radius
                ;
    }

    /************** Getters/Setters *******/

    /**
     * Get radios
     *
     * @return
     */
    public double getRadios()
    {
        return _radius;
    }


}
