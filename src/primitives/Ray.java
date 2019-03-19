package primitives;

import java.util.Objects;

/**
 *  Ray
 */
public class Ray {

    private Point3D _point3D;
    private Vector _vector;

    /********** Constructors ***********/

    /**
     * A new Ray
     * @param _point3D the start point
     * @param _vector the _vector
     */
    public Ray(Point3D _point3D, Vector _vector) {
        this._point3D = _point3D;
        this._vector = _vector;
    }

    /**
     * A new Ray
     * @param other Ray to copy from
     */
    public Ray(Ray other){
        this._point3D = new Point3D(other._point3D);
        this._vector = new Vector(other._vector);
    }

    /************** Getters/Setters *******/

    /**
     * Get start point
     * @return the point
     */
    public Point3D getPoint3D() {
        return _point3D;
    }

    /**
     * Get Vector
     * @return _vector
     */
    public Vector getVector() {
        return _vector;
    }
    /*************** Admin *****************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Ray)) return false;
        Ray ray = (Ray) o;
        return _point3D.equals(ray._point3D) &&
                _vector.equals(ray._vector);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "_point3D=" + _point3D +
                ", _vector=" + _vector +
                '}';
    }

    /************** Operations ***************/

    /**
     * add a _vector to the ray
     * @param vec the _vector to add
     * @return a new ray
     */
    public Ray add(Vector vec){
        return new Ray(_point3D,_vector.add(vec));
    }

    /**
     * subtract a _vector to the ray
     * @param vec the _vector to subtract
     * @return a new ray
     */
    public Ray subtract(Vector vec){
        return new Ray(_point3D,_vector.subtract(vec));
    }

    /**
     * scale the ray
     * @param scale the scale
     * @return a new ray
     */
    public Ray scale(double scale){
        return new Ray(_point3D,_vector.scale(scale));
    }



}
