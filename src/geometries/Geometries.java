package geometries;

import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.*;

/**
 * A container for Geometries (Intersectables)
 */
public class Geometries implements Intersectable {

    private List<Intersectable> _geometries = new ArrayList<>();

    /* ********* Constructors ***********/

    /**
     * A new Container
     * @param geometries the geometries
     * @see Intersectable
     */
    public Geometries(Intersectable ... geometries){
        _geometries.addAll(Arrays.asList(geometries));
    }

    /* ************* Operations ***************/

    /**
     * add geometry
     * @param geometries the geometry
     */
    public void add(Intersectable ... geometries){
        _geometries.addAll(Arrays.asList(geometries));
    }


    public List<Intersectable> getGeometries(){
        return _geometries;
    }


    /**
     * Intersections of all Geometries with ray
     *
     * @param ray The ray
     * @return List of intersections (Points)
     * @see Point3D#Point3D(Coordinate, Coordinate, Coordinate)
     * @see Ray#Ray(Point3D, Vector)
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        List<GeoPoint> list = new ArrayList<>();
        for (Intersectable item :_geometries) {
            list.addAll(item.findIntersections(ray));
        }
        return list;
    }

    /* ************** Admin *****************/
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("( ");
        for (Intersectable item:_geometries) {
            str.append(item.toString()).append(" ,");
        }
        str.append(" )");
        return str.toString();
    }
}
