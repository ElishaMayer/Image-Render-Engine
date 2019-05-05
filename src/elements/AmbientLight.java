package elements;

import primitives.Color;

public class AmbientLight {
    public Color _color;
    public double _ka;

    public AmbientLight(Color color, double ka) {
        if(ka<0)
            throw new IllegalArgumentException("Negative Ka");
        _color = color.scale(ka);
        _ka = ka;
    }

    public AmbientLight(AmbientLight ambientLight){
        _color = new Color(ambientLight._color);
        _ka = ambientLight._ka;
    }

    public Color getColor() {
        return _color;
    }

    public double getKa() {
        return _ka;
    }
}
