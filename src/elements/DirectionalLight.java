package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{
    @Override
    Color getIntensity() {
        return null;
    }

    @Override
    public Color getIntensity(Point3D p) {
        return null;
    }

    @Override
    public Vector getL(Point3D p) {
        return null;
    }
}
