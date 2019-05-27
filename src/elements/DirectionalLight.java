package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Light source is far away - no attenuation with distance
 */
public class DirectionalLight extends Light implements LightSource{
    Vector _direction;

    /* ********* Constructors ***********/

    /**
     * a new Directional Light
     *
     * @param color
     * @param _direction
     */
    public DirectionalLight(Color color, Vector _direction) {
        super(color);
        this._direction = new Vector( _direction);
    }

    /* ************* Getters/Setters *******/

    @Override
    Color getIntensity() {
        return null;
    }

    @Override
    public Color getIntensity(Point3D p) {
        return _color;
    }

    @Override
    public Vector getL(Point3D p) {
        return _direction.normal();
    }
}
