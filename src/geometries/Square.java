package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Square extends Plane {
    Point3D _point1;
    Point3D _point2;
    Point3D _point3;
    Point3D _point4;


    public Square(Point3D p1, Point3D p2, Point3D p3, Point3D p4, Material material, Color emission) {
        super(p1, p2, p3, material, emission);
        _point1 = new Point3D(p1);
        _point2 = new Point3D(p2);
        _point3 = new Point3D(p3);
        _point4 = new Point3D(p4);

    }

    public Square(Point3D p1, Point3D p2, Point3D p3, Point3D p4) {
        super(p1, p2, p3);
        _point1 = new Point3D(p1);
        _point2 = new Point3D(p2);
        _point3 = new Point3D(p3);
        _point4 = new Point3D(p4);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return Objects.equals(_point1, square._point1) &&
                Objects.equals(_point2, square._point2) &&
                Objects.equals(_point3, square._point3) &&
                Objects.equals(_point4, square._point4);
    }



    public Point3D getPoint1() {
        return _point1;
    }

    public Point3D getPoint2() {
        return _point2;
    }

    public Point3D getPoint3() {
        return _point3;
    }

    public Point3D getPoint4() {
        return _point4;
    }

    @Override
    public String toString() {
        return "SQ{" +
                "P1=" + _point1 +
                ", P2=" + _point2 +
                ", P3=" + _point3 +
                ", P4=" + _point4 +
                '}';
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        List<GeoPoint> intersections =  super.findIntersections(ray);
        if(intersections.isEmpty())
            return intersections;
        Point3D intsPoint = intersections.get(0).point;
        intersections.clear();
        Vector vector = ray.getVector();
        Point3D startPoint = ray.getPoint3D().subtract(ray.getVector());

        Vector v1 = _point1.subtract(startPoint);
        Vector v2 = _point2.subtract(startPoint);
        Vector v3 = _point3.subtract(startPoint);
        Vector v4 = _point4.subtract(startPoint);

        Vector n1 = v1.crossProduct(v2);
        Vector n2 = v2.crossProduct(v3);
        Vector n3 = v3.crossProduct(v4);
        Vector n4 = v4.crossProduct(v1);

        double sign1 = vector.dotProduct(n1);
        double sign2 = vector.dotProduct(n2);
        double sign3 = vector.dotProduct(n3);
        double sign4 = vector.dotProduct(n4);

        if((sign1>0&&sign2>0&&sign3>0&&sign4>0)||(sign1<0&&sign2<0&&sign3<0&&sign4<0)){
            intersections.add(new GeoPoint(this,intsPoint));
        }
        return intersections;
    }
}
