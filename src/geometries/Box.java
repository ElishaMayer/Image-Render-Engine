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
public class Box extends Intersectable{
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

    /* ************** Admin *****************/

    @Override
    public String toString() {
        return "Box{" +
                "mid=" + getMiddle() +
                '}';
    }

    /* ************* Operations ***************/



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
