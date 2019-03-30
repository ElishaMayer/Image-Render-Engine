package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * A Geometry Shape
 */
public interface Geometry {
    /**
     * Get the normal from the point in the shape
     * @param p the point
     * @return the normal
     */
    Vector getNormal(Point3D p);
}
