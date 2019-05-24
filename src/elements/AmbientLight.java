package elements;

import primitives.Color;

/**
 * Ambient light
 */
public class AmbientLight extends Light {
    //variables
    private Color _color;
    private Color _calcColor;
    private double _ka;

    /* ********* Constructors ***********/
    /**
     * A new ambient light
     * @param color the color
     * @param ka parameter
     */
    public AmbientLight(Color color, double ka) {
        if(ka<0)
            throw new IllegalArgumentException("Negative Ka");
        _calcColor = color.scale(ka);
        _color = new Color(color);
        _ka = ka;
    }

    /* ************* Getters/Setters *******/
    /**
     * get ka
     * @return ka
     */
    public double getKa() {
        return _ka;
    }

    /**
     * get color intensity
     * @return color intensity
     */
    public Color getIntensity(){
        return _calcColor;
    }

    /* ************** Admin *****************/
    @Override
    public String toString() {
        return "AL{" +
                "C=" + _color +
                ", Ka=" + _ka +
                '}';
    }
}
