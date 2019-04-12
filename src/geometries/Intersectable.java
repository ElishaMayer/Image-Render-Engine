package geometries;

import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * An Intersectable object
 */
public interface Intersectable {
    /**
     * All intersections with ray
     * @param ray The ray
     * @return List of intersections
     * @see Point3D#Point3D(Coordinate, Coordinate, Coordinate)
     * @see Ray#Ray(Point3D, Vector)
     */
    public static final List<Point3D> EMPTY_LIST = new ArrayList<>();
    List<Point3D> findIntersections(Ray ray);
}
