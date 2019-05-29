package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Models point light source with direction
 */
public class SpotLight extends PointLight {
    Vector _direction;

    /* ********* Constructors ***********/

    /**
     * a new spotlight
     *
     * @param color the color of the light
     * @param _position the position of the light source
     * @param _kC
     * @param _Kl
     * @param _Kq
     * @param _direction the direction of the light
     */
    public SpotLight(Color color, Point3D _position, double _kC, double _Kl, double _Kq, Vector _direction) {
        super(color, _position, _kC, _Kl, _Kq);
        this._direction = _direction.normal();
    }

    /* ************* Getters/Setters *******/

    @Override
    public Color getIntensity(Point3D p) {
        /*
        double distance = p.distance(_position);
        return _color.scale(Math.max(0,_direction.dotProduct(getL(p)))/(_kC+_Kl*distance+ _Kq*distance*distance));
        */
        return super.getIntensity(p).scale(Math.max(0,_direction.dotProduct(getL(p))));
    }
}
