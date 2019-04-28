package geometries;

import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * An Intersectable interface
 */
public interface Intersectable {
    /**
     * All intersections between the ray and the object
     * @param ray The ray
     * @return List of intersections (Points)
     * @see Point3D#Point3D(Coordinate, Coordinate, Coordinate)
     * @see Ray#Ray(Point3D, Vector)
     */
    List<Point3D> findIntersections(Ray ray);

    /**
     * An empty list
     */
    List<Point3D> EMPTY_LIST = new ArrayList<>();

}
