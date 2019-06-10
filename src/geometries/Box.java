package geometries;

import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Box to contain geometries
 */
public class Box extends Geometry{
    private List<Geometry> _geometries;

    /* ********* Constructors ***********/

    /**
     * A new Box
     */
    public Box() {}

    /**
     * A new Box
     * @param geometries geometries in box
     */
    public Box(List<Geometry> geometries) {
        _geometries =new ArrayList<>(geometries);
        if(!_geometries.isEmpty()) {
            Coordinate maxX = _geometries.stream().max(Comparator.comparing(x-> x.getMax().getX().get())).get().getMax().getX();
            Coordinate maxY = _geometries.stream().max(Comparator.comparing(x-> x.getMax().getY().get())).get().getMax().getY();
            Coordinate maxZ = _geometries.stream().max(Comparator.comparing(x-> x.getMax().getZ().get())).get().getMax().getZ();
            setMax(new Point3D(maxX,maxY,maxZ));
            Coordinate minX = _geometries.stream().min(Comparator.comparing(x-> x.getMax().getX().get())).get().getMax().getX();
            Coordinate minY = _geometries.stream().min(Comparator.comparing(x-> x.getMax().getY().get())).get().getMax().getY();
            Coordinate minZ = _geometries.stream().min(Comparator.comparing(x-> x.getMax().getZ().get())).get().getMax().getZ();
            setMin(new Point3D(minX,minY,minZ));
            setMiddle(new Point3D(
                    maxX.add(minX).scale(0.5),
                    maxY.add(minY).scale(0.5),
                    maxZ.add(minZ).scale(0.5)
            ));
        }
    }

    /* ************* Getters/Setters *******/

    /**
     * Get all geometries
     * @return list of geometries
     */
    public List<Geometry> getGeometries() {
        return _geometries;
    }

    /**
     * not implemented
     * @param p the point
     * @return null
     */
    @Override
    public Vector getNormal(Point3D p) {
        return null;
    }

    /* ************** Admin *****************/

    @Override
    public String toString() {
        return "Box{" +
                "mid=" + getMiddle() +
                '}';
    }

    /* ************* Operations ***************/

    /**
     * Check if ray intersects box
     * @param ray the ray
     * @return boolean
     */
    public boolean intersects(Ray ray) {
        double tmin, tmax, tymin, tymax, tzmin, tzmax;
        Point3D max = getMax();
        Point3D min = getMin();
        Point3D dir = ray.getVector().getPoint3D();
        Point3D origin = ray.getPoint3D();
        double divx = 1/dir.getX().get();
        double divy = 1/dir.getY().get();
        double divz = 1/dir.getZ().get();
        if (ray.getVector().getPoint3D().getX().get()>=0){
            tmin = min.getX().subtract(origin.getX()).scale(divx).get();
            tmax = max.getX().subtract(origin.getX()).scale(divx).get();
        }else{
            tmax = min.getX().subtract(origin.getX()).scale(divx).get();
            tmin = max.getX().subtract(origin.getX()).scale(divx).get();

        }
        if (ray.getVector().getPoint3D().getY().get()>=0){
            tymin = min.getY().subtract(origin.getY()).scale(divy).get();
            tymax = max.getY().subtract(origin.getY()).scale(divy).get();
        }else{
            tymax = min.getY().subtract(origin.getY()).scale(divy).get();
            tymin = max.getY().subtract(origin.getY()).scale(divy).get();

        }
        if ( (tmin > tymax) || (tymin > tmax) )
            return false;
        if (tymin > tmin)
            tmin = tymin;
        if (tymax < tmax)
            tmax = tymax;
        if (ray.getVector().getPoint3D().getZ().get()>=0){
            tzmin = min.getZ().subtract(origin.getZ()).scale(divz).get();
            tzmax = max.getZ().subtract(origin.getZ()).scale(divz).get();
        }else{
            tzmax = min.getZ().subtract(origin.getZ()).scale(divz).get();
            tzmin = max.getZ().subtract(origin.getZ()).scale(divz).get();
        }
        if ( (tmin > tzmax) || (tzmin > tmax) )
            return false;
        if (tzmin > tmin)
            tmin = tzmin;
        if (tzmax < tmax)
            tmax = tzmax;
        return (tmax >= 0);
    }

    /**
     * Get all intersections
     * @param ray The ray
     * @return list of geo points
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        if(intersects(ray)){
            List<GeoPoint> points = new ArrayList<>();
            for(Intersectable inter:_geometries){
                points.addAll(inter.findIntersections(ray));
            }
            return points;
        }
        return EMPTY_LIST;
    }
}
