package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Models point light source with direction
 */
public class SpotLight extends PointLight {
    private Vector _direction;

    /* ********* Constructors ***********/

    /**
     * a new spotlight
     *
     * @param color the color of the light
     * @param _position the position of the light source
     * @param _kC kc
     * @param _Kl kl
     * @param _Kq kq
     * @param _direction the direction of the light
     */
    public SpotLight(Color color, Point3D _position, double _kC, double _Kl, double _Kq, Vector _direction) {
        super(color, _position, _kC, _Kl, _Kq);
        this._direction = _direction.normal();
    }

    /* ************* Getters/Setters *******/

    /**
     * get light intensity
     * @param p the point
     * @return light
     */
    @Override
    public Color getIntensity(Point3D p) {
        double pl = _direction.dotProduct(getL(p));
        if (pl <= 0)
            return Color.BLACK;
        return super.getIntensity(p).scale(pl);
    }

    /* ************* Administration *******/

    @Override
    public String toString() {
        return "SL{" +
                "D=" + _direction +
                ", C=" + super.toString() +
                '}';
    }
}
