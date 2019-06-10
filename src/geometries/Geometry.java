package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * A Geometry Shape (interface)
 */
public abstract class Geometry implements Intersectable{
    private Color _emission;
    private Material _material;
    private Point3D _minimum;
    private Point3D _maximum;
    private Point3D _middle;

    /************** Getters/Setters *******/

    /**
     * Get the normal from the point in the shape
     * @param p the point
     * @return the normal
     */
    abstract public Vector getNormal(Point3D p);

    /**
     * Get object emission
     * @return emission color
     */
    public Color getEmission(){
        return _emission;
    }

    /**
     * Get object material
     * @return material
     */
    public Material getMaterial(){
        return _material;
    }

    /**
     * get maximum point
     * @return 3d point
     */
    public Point3D getMax(){
        return _maximum;
    }

    /**
     * get minimum point
     * @return 3d point
     */
    public Point3D getMin(){
        return  _minimum;
    }

    /**
     * Set minimum
     * @param m point
     */
    public void setMin(Point3D m){
        _minimum = new Point3D(m);
    }

    /**
     * Set maximum
     * @param m point
     */
    public void setMax(Point3D m){
        _maximum = new Point3D(m);
    }

    /**
     * Get middle point
     * @return point
     */
    public Point3D getMiddle() {
        return _middle;
    }

    /**
     * set middle point
     * @param middle point
     */
    public void setMiddle(Point3D middle) {
        this._middle = new Point3D(middle);
    }

    /********** Constructors ***********/
    // for the classes who derived from this abstract class

    public Geometry(){
        _emission = new Color(0,0,0);
        _material = new Material();
    }

    public Geometry(Material material, Color emission){
        _material = new Material(material);
        _emission = new Color(emission);
    }

}
