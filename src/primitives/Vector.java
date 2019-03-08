package primitives;

public class Vector {
    private Point3D _point3D;

    public Vector(Point3D _point3D) {
        this._point3D = _point3D;
    }

    public Point3D get_point3D() {
        return _point3D;
    }
}
