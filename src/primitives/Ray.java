package primitives;

import java.util.Objects;

/**
 *  Ray
 */
public class Ray {

    private Point3D point3D;
    private Vector vector;

    /********** Constructors ***********/

    /**
     * A new Ray
     * @param point3D the start point
     * @param vector the vector
     */
    public Ray(Point3D point3D, Vector vector) {
        this.point3D = point3D;
        this.vector = vector;
    }

    /**
     * A new Ray
     * @param other Ray to copy from
     */
    public Ray(Ray other){
        this.point3D = new Point3D(other.point3D);
        this.vector = new Vector(other.vector);
    }

    /************** Getters/Setters *******/

    /**
     * Get start point
     * @return the point
     */
    public Point3D getPoint3D() {
        return point3D;
    }

    /**
     * Get Vector
     * @return vector
     */
    public Vector getVector() {
        return vector;
    }
    /*************** Admin *****************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Ray)) return false;
        Ray ray = (Ray) o;
        return point3D.equals(ray.point3D) &&
                vector.equals(ray.vector);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "point3D=" + point3D +
                ", vector=" + vector +
                '}';
    }

    /************** Operations ***************/

    /**
     * add a vector to the ray
     * @param vec the vector to add
     * @return a new ray
     */
    public Ray add(Vector vec){
        return new Ray(point3D,vector.add(vec));
    }

    /**
     * subtract a vector to the ray
     * @param vec the vector to subtract
     * @return a new ray
     */
    public Ray subtract(Vector vec){
        return new Ray(point3D,vector.subtract(vec));
    }

    /**
     * scale the ray
     * @param scale the scale
     * @return a new ray
     */
    public Ray scale(double scale){
        return new Ray(point3D,vector.scale(scale));
    }



}
