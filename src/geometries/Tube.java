package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Tube
 */
public class Tube extends RadialGeometry implements Geometry{
    Ray _ray;

    /* ******* Constructors ***********/

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


    /* ************* Getters/Setters *******/

    /**
     * Get Vector
     *
     * @return vector
     */
    public Ray getRay() {
        return _ray;
    }

    /* ************** Admin *****************/

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
        //get ray point and vector
        Point3D rayP = _ray.getPoint3D();
        Vector rayV = _ray.getVector();

        //get point on the same level as the given point
        double t = rayV.dotProduct(p.subtract(rayP));

        //if the point is not on the same level then get the point
        //and retur the normal
        if(!Util.isZero(t)){
            Point3D o = rayP.add(rayV.scale(t));
            return p.subtract(o).normal();
        }

        //if the pint is on hte same level then return normal
        return p.subtract(_ray.getPoint3D()).normal();
    }

    /**
     * Returns all intersections with ray
     *
     * @param ray The ray
     * @return List of intersections (Points)
     * @see Point3D#Point3D(Coordinate, Coordinate, Coordinate)
     * @see Ray#Ray(Point3D, Vector)
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {

        Point3D d;
        Point3D e;
        double dis;
        double ab;

        //Given ray (A + ta)
        Point3D pointA = ray.getPoint3D();
        Vector vectorA = ray.getVector();
        //Tube ray (B + tb)
        Point3D pointB = _ray.getPoint3D();
        Vector vectorB = _ray.getVector();

        try {
            //Vector AB
            Vector c = pointB.subtract(pointA);
            //dot-product calc
            ab = vectorA.dotProduct(vectorB);
            double bc = vectorB.dotProduct(c);
            double ac = vectorA.dotProduct(c);
            double bb = vectorB.dotProduct(vectorB);
            double aa = vectorA.dotProduct(vectorA);

            //The closest point on (A + t1a)
            double t1 = (-ab * bc + ac * bb) / (aa * bb - ab * ab);
            try {
                d = pointA.add(vectorA.scale(t1));
            } catch (Exception ex) {
                d = pointA;
            }

            //The closest point on (B + t2b)
            double t2 = (ab * ac - bc * aa) / (aa * bb - ab * ab);


            try {
                e = pointB.add(vectorB.scale(t2));
            } catch (Exception ex) {
                e = pointB;
            }

            //distance between two rays
            dis = d.distance(e);

        } catch (Exception ex) {
            //If A and B are the same
            d = new Point3D(ray.getPoint3D());
            ab = vectorA.dotProduct(vectorB);
            dis = 0;
        }
        //if is parallel to tube
        if (Util.isOne(Math.abs(ab))) {
            return EMPTY_LIST;
        }

        //The ray doesn't touch the Tube
        if (Util.usubtract(dis, _radius) > 0.0)
            return EMPTY_LIST;

        //The ray is tangent to the Tube
        if (Util.usubtract(dis, _radius) == 0.0) {
            //The ray starts at the point
            if (d.equals(pointA)) {
                List<Point3D> list = new ArrayList<>();
                list.add(d);
                return list;
            }
            //The ray starts after the point
            if (d.subtract(pointA).dotProduct(vectorA) < 0.0) {
                return EMPTY_LIST;
            }
            List<Point3D> list = new ArrayList<>();
            //The ray starts before the point
            list.add(d);
            return list;
        }

        /*We know that the ray goes through the tube.
         *Lets cut the tube parallel to the ray.
         * We will get a ellipse where the height is _radius.
         * We need to calculate the width
         */
        double width;
        //if the ray is orthogonal to the tube
        try {
            //sin's between (B + tb) and (A + ta) is |VxU|
            double sinA = vectorA.crossProduct(vectorB).length() ;
            //ellipse width
            width = _radius / sinA;
        } catch (Exception ex) {
            width = _radius;
        }
        //ellipse equation x^2/k^2 + y^2 = _radius^2
        //if the width is w then k is w/r
        double k = width / _radius;
        //y is d for our ray x^2/k^2 + k^2 = _radius^2 => x^2/k^2 = _radius^2 -d^2 =>
        // x^2 = (_radius^2 -d^2)*k^2 => x = sqrt(_radius^2 -d^2)*k
        double th = Math.sqrt(_radius * _radius - dis * dis) * k;

        //the two points
        Point3D p1 = d.subtract(vectorA.scale(th));
        Point3D p2 = d.add(vectorA.scale(th));

        //Check if the points are in range and return them
        List<Point3D> list = new ArrayList<>();

        try {
            //the ray starts before point 1
            if (!(p1.subtract(pointA).dotProduct(vectorA) < 0.0)) {
                list.add(p1);
            }
        } catch (Exception ex) {
            //the ray starts at point1
            list.add(p1);
        }


        try {
            //the ray starts before point 2
            if (!(p2.subtract(pointA).dotProduct(vectorA) < 0.0)) {
                list.add(p2);
            }
        } catch (Exception ex) {
            //the ray starts at point2
            list.add(p2);
        }

        return list;

    }

    /* ************* Operations ***************/
}
