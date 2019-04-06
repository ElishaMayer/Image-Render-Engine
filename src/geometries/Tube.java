package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Tube
 */
public class Tube extends RadialGeometry implements Geometry{
    protected Ray _ray;

    /********** Constructors ***********/

    /**
     * A new Tube
     *
     * @param radius the radius
     * @param ray the direction
     */
    public Tube(double radius, Ray ray) {
        super(radius);
        _ray = new Ray(ray);
    }


    /************** Getters/Setters *******/

    /**
     * Get Vector
     *
     * @return vector
     */
    public Ray getRay() {
        return _ray;
    }

    /*************** Admin *****************/

    @Override
    public String toString() {
        return "Tube{" + super.toString() +
                ", " + _ray +
                "}";
    }

    /**
     * Get the normal from the point in the shape
     *
     * @param p the point
     * @return the normal
     */
    @Override
    public Vector getNormal(Point3D p) {
        double t = _ray.getVector().dotProduct(p.subtract(_ray.getPoint3D()));
        if(t!=0){
            Point3D o = _ray.getPoint3D().add(_ray.getVector().scale(t));
            return p.subtract(o).normal();
        }

        return p.subtract(_ray.getPoint3D()).normal();
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
        List<Point3D> list = new ArrayList<>();
        Point3D D;
        Point3D E;
        double d;
        double ab;

        //Given ray (A + ta)
        Point3D A = ray.getPoint3D();
        Vector a = ray.getVector();
        //Tube ray (B + tb)
        Point3D B = _ray.getPoint3D();
        Vector b = _ray.getVector();
        //If A and B are the same
        if(B.equals(A)){
            E = new Point3D(ray.getPoint3D());
            D = new Point3D(ray.getPoint3D());
            ab = a.dotProduct(b);
            d=0;
        }else{
            //Vector AB
            Vector c = B.subtract(A);
            //dot-product calc
            ab = a.dotProduct(b);
            double bc = b.dotProduct(c);
            double ac = a.dotProduct(c);
            double bb = b.dotProduct(b);
            double aa = a.dotProduct(a);

            //The closest point on (A + t1a)
            double t1 = (-ab*bc+ac*bb)/(aa*bb - ab*ab);
            if(Util.isZero(t1))
                D = A;
            else
                D = A.add(a.scale(t1));

            //The closest point on (B + t2b)
            double t2 =(ab*ac-bc*aa)/(aa*bb-ab*ab);
            if(Util.isZero(t2))
                E = B;
            else
                E = B.add(b.scale(t2));

            //distance between two rays
            d = D.distance(E);

        }
        //if is parallel to tube
        if(Util.isOne(ab)){
            if(Util.usubtract(d,_radius)==0.0)
                throw new IllegalArgumentException("ray is parallel to tube");
            else
                return list;
        }

        //The ray doesn't touch the Tube
        if(Util.usubtract(d,_radius)>0.0)
            return list;

        //The ray is tangent to the Tube
        if(Util.usubtract(d,_radius) == 0.0){
            //The ray starts at the point
            if(D.equals(ray.getPoint3D())){
                list.add(D);
                return list;
            }
            //The ray starts after the point
            if(D.subtract(ray.getPoint3D()).dotProduct(ray.getVector())<0.0){
                return list;
            }
            //The ray starts before the point
            list.add(D);
            return list;
        }

        /*We know that the ray goes through the tube.
         *Let cut the tube parallel to the ray.
         * We will get a ellipse where the height is _radius.
         * We need to calculate the width
         */
        double width;
        //if the ray is orthogonal to the tube
        if(Util.isZero(ab))
            width = _radius;
        else {
            //tang's between (B + tb) and (A + ta) is |VxU|/V.U
            double tanA = (a.crossProduct(b).length() / a.dotProduct(b));
            double heightOnTube = _radius / tanA;
            //ellipse width
            width = Math.sqrt(heightOnTube * heightOnTube + _radius * _radius);
        }
        //ellipse equlation x^2/k^2 + y^2 = _radius^2
        //if the width is w then k is w/r
        double k = width/_radius;
        //y is d for our ray x^2/k^2 + k^2 = _radius^2 => x^2/k^2 = _radius^2 -d^2 =>
        // x^2 = (_radius^2 -d^2)*k^2 => x = sqrt(_radius^2 -d^2)*k
        double th = Math.sqrt(_radius*_radius - d*d)*k;

        //the two points
        Point3D p1 = D.subtract(a.scale(th));
        Point3D p2 = D.add(a.scale(th));

        //the ray starts at point1
        if(p1.equals(ray.getPoint3D())){
            list.add(p1);
            list.add(p2);
            return list;
        }
        //the ray starts at point2
        if(p2.equals(ray.getPoint3D())){
            list.add(p2);
            return list;
        }
        //the ray starts between the two points
        if(p1.subtract(ray.getPoint3D()).dotProduct(ray.getVector())<0.0){
            list.add(p2);
            return list;
        }
        //the ray starts after the two points
        if(p2.subtract(ray.getPoint3D()).dotProduct(ray.getVector())<0.0){
            return list;
        }
        //the ray starts before the two points
        list.add(p1);
        list.add(p2);
        return list;

    }

    /************** Operations ***************/
}
