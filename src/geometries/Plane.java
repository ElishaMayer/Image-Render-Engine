package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.isOne;
import static primitives.Util.isZero;

/**
 * Plane
 */
public class Plane implements Geometry{

    protected Point3D _point;
    private Vector _vector;

    /********** Constructors ***********/

    /**
     * A new Plane
     *
     * @param p  a point on the plane
     * @param vec a vector in the plane
     */
    public Plane(Point3D p, Vector vec) {
        _point = new Point3D(p);
        _vector = vec.normal();
    }

    /**
     * A new Plane
     * @param p1 Point 1
     * @param p2 Point 2
     * @param p3 Point 3
     */
    public Plane(Point3D p1 , Point3D p2 ,Point3D p3){
        _point = new Point3D(p1);
        Vector v1 = p2.subtract(p3);
        Vector v2 = p2.subtract(p1);
        try {
            _vector = v1.crossProduct(v2).normal();
        }catch (IllegalArgumentException exc){
            throw new IllegalArgumentException("This is not a Plane/Triangle");
        }
    }

    /*************** Admin *****************/

    @Override
    public String toString() {
        return "Plane{" + _point + ", " + _vector + "}";
    }

    /************** Getters/Setters *******/

    /**
     * Get Point
     *
     * @return point
     */
    public Point3D getPoint() {
        return _point;
    }

    /**
     * Get Vector
     *
     * @return vector
     */
    public Vector getVector() {
        return _vector;
    }

    /**
     * Get the normal from the point in the shape
     *
     * @param p the point
     * @return the normal
     */
    @Override
    public Vector getNormal(Point3D p) {
        return new Vector(_vector);
    }

    /**
     * All intections with ray
     *
     * @param ray The ray
     * @return List of intersactions
     * @see Point3D#Point3D(Coordinate, Coordinate, Coordinate)
     * @see Ray#Ray(Point3D, Vector)
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        //list to return
        List<Point3D> list = new ArrayList<>();

        // the ray is parallel to the plane
        if (isZero(ray.getVector().dotProduct(_vector.normal()))) // dotProduct with normal = 0 => parallel
            if (isZero(_vector.dotProduct(_point.subtract(ray.getPoint3D())))) //the starting point of the ray is on the plane
                throw new IllegalArgumentException("ray is included in the plane");
            else
                return list;

        /*
        Ray points: P=P0+t∙v, , t≥0
        Plane points: Plane points: N∙(Q0−P)=0
        N∙(Q0−t∙v−P0)=0
        N∙(Q0−P0)−t∙N∙v=0
        t=N∙(Q0−P0)/(N∙v)
        */
        double t = (_vector.dotProduct(_point.subtract(ray.getPoint3D())))/
                (_vector.dotProduct(ray.getVector()));

        if(isZero(t)) // the ray starts on the plane
            list.add(ray.getPoint3D());
        else if(Util.usubtract(t,0.0) > 0.0) // the ray crosses the plane
            list.add(ray.getPoint3D().add(ray.getVector().scale(t)));

        return list;
    }

    /************** Operations ***************/

}
