package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

public class Cube extends Geometry {
    Geometries _geometries;
    Point3D _middle;

    public Cube(Square front,Square back,Material material, Color emission) {
        super(material, emission);
        _geometries = new Geometries(front,back);
        _geometries.add(new Square(front.getPoint1(),front.getPoint2(),back.getPoint2(),back.getPoint1()));
        _geometries.add(new Square(front.getPoint2(),front.getPoint3(),back.getPoint3(),back.getPoint2()));
        _geometries.add(new Square(front.getPoint3(),front.getPoint4(),back.getPoint4(),back.getPoint3()));
        _geometries.add(new Square(front.getPoint4(),front.getPoint1(),back.getPoint1(),back.getPoint4()));
        Vector cross = front.getPoint1().subtract(front.getPoint3());
        Point3D middleF = front.getPoint3().add(cross.scale(0.5));
        cross = back.getPoint1().subtract(back.getPoint3());
        Point3D middleB = back.getPoint3().add(cross.scale(0.5));
        cross = middleB.subtract(middleF);
        _middle = middleF.add(cross.scale(0.5));
    }

    public Cube(Square front,Point3D bp,double height,Material material, Color emission) {
        super(material, emission);
        Vector normal = front.getNormal(null);
        Vector dir = bp.subtract(front.getPoint3());
        if(dir.dotProduct(normal)>0){
            normal = normal.scale(-1);
        }
        Vector h = normal.scale(height);
        Square back = new Square(front.getPoint1().add(h),front.getPoint2().add(h),front.getPoint3().add(h),front.getPoint4().add(h));
        _geometries = new Geometries(front,back);
        _geometries.add(new Square(front.getPoint1(),front.getPoint2(),back.getPoint2(),back.getPoint1()));
        _geometries.add(new Square(front.getPoint2(),front.getPoint3(),back.getPoint3(),back.getPoint2()));
        _geometries.add(new Square(front.getPoint3(),front.getPoint4(),back.getPoint4(),back.getPoint3()));
        _geometries.add(new Square(front.getPoint4(),front.getPoint1(),back.getPoint1(),back.getPoint4()));
        Vector cross = front.getPoint1().subtract(front.getPoint3());
        Point3D middleF = front.getPoint3().add(cross.scale(0.5));
        cross = back.getPoint1().subtract(back.getPoint3());
        Point3D middleB = back.getPoint3().add(cross.scale(0.5));
        cross = middleB.subtract(middleF);
        _middle = middleF.add(cross.scale(0.5));
    }

    @Override
    public Vector getNormal(Point3D p) {
        Geometry geometry = _geometries.findIntersections(new Ray(_middle,p.subtract(_middle))).get(0).geometry;
        return geometry.getNormal(p);
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        List<GeoPoint> intersections = _geometries.findIntersections(ray);
        if(intersections.isEmpty())
            return  intersections;
        List<GeoPoint> returnInts = new ArrayList<>();
        for(GeoPoint geoPoint:intersections){
            returnInts.add(new GeoPoint(this,geoPoint.point));
        }
        return returnInts;
    }
}
