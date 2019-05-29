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
        this._direction = new Vector( _direction);
    }

    /* ************* Getters/Setters *******/

    /**
     * not implemented
     * @return null
     */
    @Override
    Color getIntensity() {
        return null;
    }

    /**
     * get light intensity
     * @param p the point
     * @return light intensity
     */
    @Override
    public Color getIntensity(Point3D p) {
        return _color;
    }

    /**
     * get vector from light
     * @param p the point
     * @return vector
     */
    @Override
    public Vector getL(Point3D p) {
        return _direction.normal();
    }

    /* ************* Administration *******/
    @Override
    public String toString() {
        return "DL{" +
                "dir=" + _direction +
                ", c=" + _color +
                '}';
    }
}
