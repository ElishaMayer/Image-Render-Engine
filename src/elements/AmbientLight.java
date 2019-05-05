package elements;

import primitives.Color;

public class AmbientLight {
    private Color _color;
    private double _ka;

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

    public double getKa() {
        return _ka;
    }

    @Override
    public String toString() {
        return "AL{" +
                "C=" + _color +
                ", Ka=" + _ka +
                '}';
    }

    public Color GetIntensity(){
        return _color;
    }
}
