package elements;

import primitives.Color;

/**
 * Abstract Light
 */
public abstract class Light {

    Color _color;

    /**
     * A new Light
     */
    public Light(){
        _color = new Color();
    }

    /**
     * A new Light
     * @param color the color
     */
    public Light(Color color){
        _color = new Color(color);
    }

    /**
     * get Color Intensity
     * @return color Intensity
     */
    abstract Color getIntensity();
}
