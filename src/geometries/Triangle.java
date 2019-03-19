package geometries;

import primitives.Point3D;

/**
 * Triangle
 */
public class Triangle {
    private Point3D _point1;
    private Point3D _point2;
    private Point3D _point3;

    /**
     * A new Triangle
     * @param _point1
     * @param _point2
     * @param _point3
     */
    public Triangle(Point3D _point1, Point3D _point2, Point3D _point3) {
        this._point1 = new Point3D( _point1);
        this._point2 = new Point3D( _point2);
        this._point3 = new Point3D( _point3);
    }
    public Triangle(Triangle other){
        this._point1 = new Point3D( other._point1);
        this._point2 = new Point3D( other._point2);
        this._point3 = new Point3D( other._point3);
    }

    public Point3D get_point1() {
        return _point1;
    }

    public Point3D get_point2() {
        return _point2;
    }

    public Point3D get_point3() {
        return _point3;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "_point1=" + _point1 +
                ", _point2=" + _point2 +
                ", _point3=" + _point3 +
                '}';
    }
}
