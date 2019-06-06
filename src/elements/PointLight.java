package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Models omni-directional point source
 */
public class PointLight extends Light implements LightSource {
    private Point3D _position;
    private double _kC;
    private double _Kl;
    private double _Kq;

    /* ********* Constructors ***********/

    /**
     * a new point light
     *
     * @param color the color of the light
     * @param _position the position of the light source
     * @param _kC kc
     * @param _Kl kl
     * @param _Kq kq
     */
    public PointLight(Color color, Point3D _position, double _kC, double _Kl, double _Kq) {
        super(color);
        this._position = new Point3D(_position);
        this._kC = _kC;
        this._Kl = _Kl;
        this._Kq = _Kq;
    }

    /* ************* Getters/Setters *******/
    /**
     * get light intensity
     * @param p the point
     * @return light
     */
    @Override
    public Color getIntensity(Point3D p) {
        double distance2 = p.distance2(_position);
        return getIntensity().scale(1/(_kC+_Kl*Math.sqrt(distance2) + _Kq*distance2));
    }

    /**
     * get vector from light
     * @param p the point
     * @return vector
     */
    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position).normal();
    }

    /* ************* Administration *******/
    @Override
    public String toString() {
        return "PL{" +
                "P=" + _position +
                ", kC=" + _kC +
                ", Kl=" + _Kl +
                ", Kq=" + _Kq +
                ", C=" + super.toString() +
                '}';
    }
}
