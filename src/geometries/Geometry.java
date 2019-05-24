package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * A Geometry Shape (interface)
 */
public abstract class Geometry implements Intersectable{
    private Color _emission;
    /**
     * Get the normal from the point in the shape
     * @param p the point
     * @return the normal
     */
    abstract public Vector getNormal(Point3D p);

    /**
     * Get object emission
     * @return
     */
    public Color getEmission(){
        return _emission;
    }
}
