package elements;

import primitives.Color;

/**
 * Ambient light
 */
public class AmbientLight {
    //variables
    private Color _color;
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
        _color = color.scale(ka);
        _ka = ka;
    }

    /**
     * A new Ambient light
     * @param ambientLight other ambient light
     */
    public AmbientLight(AmbientLight ambientLight){
        _color = new Color(ambientLight._color);
        _ka = ambientLight._ka;
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
    public Color GetIntensity(){
        return _color;
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