package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Models omni-directional point source
 */
public class PointLight extends Light implements LightSource {
    Point3D _position;
    double _kC;
    double _Kl;
    double _Kq;

    /* ********* Constructors ***********/

    /**
     * a new point light
     *
     * @param color the color of the light
     * @param _position the position of the light source
     * @param _kC
     * @param _Kl
     * @param _Kq
     */
    public PointLight(Color color, Point3D _position, double _kC, double _Kl, double _Kq) {
        super(color);
        this._position = new Point3D(_position);
        this._kC = _kC;
        this._Kl = _Kl;
        this._Kq = _Kq;
    }

    /* ************* Getters/Setters *******/

    @Override
    Color getIntensity() {
        return null;
    }

    @Override
    public Color getIntensity(Point3D p) {
        double distance = p.distance(_position);
        return _color.scale(1/(_kC+_Kl*distance+ _Kq*distance*distance));
    }

    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position).normal();
    }
}
