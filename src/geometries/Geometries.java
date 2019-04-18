package geometries;

import javafx.beans.binding.BooleanBinding;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A container for Geometries (Intersectables)
 */
public class Geometries implements Intersectable {

    private List<Intersectable> _geometries = new ArrayList<>();

    /********** Constructors ***********/

    /**
     * A new Container
     * @param geometries the geometries
     * @see Intersectable
     */
    public Geometries(Intersectable ... geometries){
        _geometries.addAll(Arrays.asList(geometries));
    }

    /************** Operations ***************/

    /**
     * add geometry
     * @param geometries the geometry
     */
    public void add(Intersectable ... geometries){
        _geometries.addAll(Arrays.asList(geometries));
    }

    /**
     * All intersections with ray
     *
     * @param ray The ray
     * @return List of intersections
     * @see Point3D#Point3D(Coordinate, Coordinate, Coordinate)
     * @see Ray#Ray(Point3D, Vector)
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> list = new ArrayList<>();
        for (Intersectable item :_geometries) {
            list.addAll(item.findIntersections(ray));
        }

        return list;
    }

    /*************** Admin *****************/
    @Override
    public String toString() {
        String str = "( ";
        for (Intersectable item:_geometries) {
            str += item.toString() + " ,";
        }
        str+=" )";
        return str;
    }
}
