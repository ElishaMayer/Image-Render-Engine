package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Light source is far away - no attenuation with distance
 */
public class DirectionalLight extends Light implements LightSource{
    private Vector _direction;

    /* ********* Constructors ***********/

    /**
     * a new Directional Light
     *
     * @param color color
     * @param _direction light direction
     */
    public DirectionalLight(Color color, Vector _direction) {
        super(color);
        this._direction = _direction.normal();
    }

    /* ************* Getters/Setters *******/
    /**
     * get light intensity
     * @param p the point
     * @return light intensity
     */
    @Override
    public Color getIntensity(Point3D p) {
        return getIntensity();
    }

    /**
     * get vector from light
     * @param p the point
     * @return vector
     */
    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }

    /* ************* Administration *******/
    @Override
    public String toString() {
        return "DL{" +
                "dir=" + _direction +
                super.toString() +
                '}';
    }
}
