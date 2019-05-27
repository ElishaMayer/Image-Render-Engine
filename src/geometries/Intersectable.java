package geometries;

import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    List<GeoPoint> findIntersections(Ray ray);

    /**
     * An empty list
     */
    List<GeoPoint> EMPTY_LIST = new ArrayList<>();


    static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        /* ********* Constructors ***********/

        public GeoPoint(Geometry geometry,Point3D point){
            this.geometry = geometry;
            this.point = point;
        }

        /* ************** Admin *****************/

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) &&
                    point.equals( geoPoint.point);
        }

        @Override
        public String toString() {
            return "GP{" +
                    "G=" + geometry +
                    ", P=" + point +
                    '}';
        }
    }

}
