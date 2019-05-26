package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class SpotLight extends PointLight {
    Vector _direction;

    public SpotLight(Color color, Point3D _position, double _kC, double _Kl, double _Kq, Vector _direction) {
        super(color, _position, _kC, _Kl, _Kq);
        this._direction = _direction.normal();
    }

    @Override
    public Color getIntensity(Point3D p) {
        double distance = p.distance(_position);
        return _color.scale(Math.max(0,_direction.dotProduct(getL(p)))/(_kC+_Kl*distance+ _Kq*distance*distance));    }
}
