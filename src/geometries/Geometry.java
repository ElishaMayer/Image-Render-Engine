package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * A Geometry Shape (interface)
 */
public abstract class Geometry implements Intersectable{
    protected Color _emission;
    protected Material _material;
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

    public Material getMaterial(){
        return _material;
    }

    public Geometry(){
        _emission = new Color(0,0,0);
        _material = new Material();
    }

    public Geometry(Material material,Color emission){
        _material = new Material(material);
        _emission = new Color(emission);
    }
}
