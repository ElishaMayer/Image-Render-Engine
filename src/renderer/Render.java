package renderer;

import elements.Camera;
import elements.LightSource;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static geometries.Intersectable.GeoPoint;

/**
 * Render class (makes a bitmap picture from the scene)
 */
public class Render {
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final double EPS = 0.1;
    private static final int RECURSIVE_L = 5;
    private static final int RAY_BEAM = 10;



    // variables
    private ImageWriter _imageWriter;
    private Scene _scene;

    /* ********* Constructors ***********/

    /**
     * a new Render
     *
     * @param imageWriter image writer
     * @param scene       scene
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        _imageWriter = imageWriter;
        _scene = scene;
    }

    /* ************* Getters/Setters *******/

    /**
     * Get image writer
     *
     * @return image writer
     */
    public ImageWriter getImageWriter() {
        return _imageWriter;
    }

    /**
     * Get scene
     *
     * @return scene
     */
    public Scene getScene() {
        return _scene;
    }

    /* ************* Operations ***************/

    /**
     * render the scene into the imageWriter
     */
    public void renderImage() {

        //variables
        int nx = _imageWriter.getNx();
        int ny = _imageWriter.getNy();
        double width = _imageWriter.getWidth();
        double height = _imageWriter.getHeight();
        Camera camera = _scene.getCamera();
        double distance = _scene.getCameraDistance();
        java.awt.Color background = _scene.getBackground().getColor();
        double time = 0;
        int whole = nx*ny;
        int prev = 0;
        long start = (long) (System.currentTimeMillis()/1000.0);

        //render image
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                int pers = (int) ((++time/whole)*1000);
                if( pers!=prev) {
                    System.out.println(pers / 10.0 + "%");
                    prev = pers;
                }
                Ray ray = camera.constructRayThroughPixel(nx, ny, i, j, distance, width, height);
                GeoPoint intersection = getClosestPoint(getSceneIntersections(ray, -1));
                _imageWriter.writePixel(i, j,
                        intersection == null ? background : // no intersection = background color
                                calcColor(intersection, ray).getColor()); // intersection = calculate the right color
            }
        }
        long end = (long) (System.currentTimeMillis()/1000.0);
        int  elapsed = (int) ((end - start));
        int hours = (int) (elapsed/600.0);
        elapsed = elapsed%600;
        int minuts = (int) (elapsed/60.0);
        elapsed = elapsed % 60;
        int seconds = elapsed;
        System.out.println(hours+"h "+minuts+"m "+seconds+"s");

    }

    /**
     * calculate the color of a point in the scene
     *
     * @param geopoint the point which we calculate the color from
     * @param inRay    the ray to the given point
     * @return the color of the requested point
     */
    private Color calcColor(GeoPoint geopoint, Ray inRay) {
        return calcColor(geopoint, inRay, RECURSIVE_L, 1.0).add(_scene.getAmbient().getIntensity());
    }

    /**
     * calculate the color of a point in the scene
     *
     * @param geopoint the point which we calculate the color from
     * @param inRay    the ray to the given point
     * @param level    the number of recursion levels
     * @param k        double num that if it is smaller than MIN_CALC_COLOR_K we will return BLACK
     * @return the color of the requested point
     */
    private Color calcColor(GeoPoint geopoint, Ray inRay, int level, double k) {
        if (level == 0 || k < MIN_CALC_COLOR_K) return Color.BLACK;

        //add emission
        Color color = geopoint.geometry.getEmission();

        //get variables
        Vector v = geopoint.point.subtract(_scene.getCamera().getP0()).normal();
        Vector n = geopoint.geometry.getNormal(geopoint.point);
        int nShininess = geopoint.geometry.getMaterial().getNShininess();
        double kd = geopoint.geometry.getMaterial().getKD();
        double ks = geopoint.geometry.getMaterial().getKS();
        //go over lights
        for (LightSource lightSource : _scene.getLights()) {
            Vector l = lightSource.getL(geopoint.point);
            if (n.dotProduct(l) * n.dotProduct(v) > 0) {
                double ktr = transparency(l, n, geopoint);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    //add light
                    Color lightIntensity = lightSource.getIntensity(geopoint.point).scale(ktr);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }

        // Recursive call for a reflected ray
        Color sum = new Color(0,0,0);
        double kr = geopoint.geometry.getMaterial().getKR();
        double rr = geopoint.geometry.getMaterial().getRR();
        Vector direction;
        Vector newV = inRay.getVector();
        double kkr = k * kr;
        if (kkr > MIN_CALC_COLOR_K) {
            try {
                // reflectedRay vector = v-2∙(v∙n)∙n
                direction = newV.subtract(n.scale(2 * newV.dotProduct(n)));
                Ray reflectedRay = new Ray(geopoint.point.add(direction), direction);

                if(rr!=0) {
                    List<Ray> beam = getBeam(reflectedRay, n, rr, RAY_BEAM);
                    for (Ray beamRay : beam) {
                        GeoPoint reflectedPoint = getClosestPoint(getSceneIntersections(beamRay, -1));
                        if (reflectedPoint != null) {
                            sum = sum.add(calcColor(reflectedPoint, beamRay, level - 1, kkr).scale(kr));
                        }
                    }
                    color = color.add(sum.scale(1.0 / beam.size()));

                }else{
                    GeoPoint reflectedPoint = getClosestPoint(getSceneIntersections(reflectedRay, -1));
                    if (reflectedPoint != null) {
                        color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
                    }
                }
            } catch (Exception ignored) {
            }
        }

        // Recursive call for a refracted ray
        sum = new Color(0,0,0);
        double kt = geopoint.geometry.getMaterial().getKT();
        double rt = geopoint.geometry.getMaterial().getRT();
        double kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K) {
            direction = newV;
            Ray refractedRay = new Ray(geopoint.point.add(direction), direction);

            if(rt!=0) {
                List<Ray> beam = getBeam(refractedRay, n, rt, RAY_BEAM);
                for (Ray beamRay : beam) {
                    GeoPoint refractedPoint = getClosestPoint(getSceneIntersections(beamRay, -1));
                    if (refractedPoint != null)
                        sum = sum.add(calcColor(refractedPoint, beamRay, level - 1, kkt).scale(kt));
                }
                color = color.add(sum.scale(1.0/beam.size()));

            }else{
                GeoPoint refractedPoint = getClosestPoint(getSceneIntersections(refractedRay, -1));
                if (refractedPoint != null)
                    color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
            }
        }

        return color;
    }

    /**
     * calculate the Diffusive factor
     *
     * @param kd             kd
     * @param l              vector from light
     * @param n              normal
     * @param lightIntensity light intensity
     * @return diffusive light
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        // kd∙|l∙n|∙lightIntensity
        return lightIntensity.scale(kd * Math.abs(l.dotProduct(n)));
    }

    /**
     * calculate the Specular factor
     *
     * @param ks             ks
     * @param l              vector from light
     * @param n              normal
     * @param v              vector v
     * @param nShininess     material shininess
     * @param lightIntensity light intensity
     * @return specular light
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r;
        //get vector r
        try {
            r = l.subtract(n.scale(2 * l.dotProduct(n))).normal(); // r = l - 2∙(l∙n)∙𝒏
        } catch (Exception ex) {
            r = l;
        }
        double minusvr = -v.dotProduct(r);
        if (minusvr <= 0)
            return Color.BLACK;
        // ks∙(max(0,-v∙r))^nShininess∙lightIntensity
        return lightIntensity.scale(ks * Math.pow(minusvr, nShininess));
    }

    /**
     * find the point with the minimal distance from the camera from the given intersection points list
     *
     * @param list the intersection points with a ray in the scene
     * @return the closet point to the camera, or null if there are no intersection points
     */
    private GeoPoint getClosestPoint(List<GeoPoint> list) {
        Point3D p = _scene.getCamera().getP0();

        if (list.size() == 0)
            return null;
        GeoPoint pToReturn = list.get(0);
        if (list.size() == 1)
            return pToReturn;

        double distance2 = p.distance2(pToReturn.point);

        for (GeoPoint point : list) {
            double temp = p.distance2(point.point);
            if (temp < distance2) {
                pToReturn = point;
                distance2 = temp;
            }
        }

        return pToReturn;
    }

    /**
     * add a grid on top of the image writer
     *
     * @param interval the distance between the lines of the grid
     */
    public void printGrid(int interval) {
        int nx = _imageWriter.getNx();
        int ny = _imageWriter.getNy();
        for (int i = 0; i < nx; i++) {
            for (int j = 0; j < ny; j++) {
                if (((i + 1) % interval == 0 || (j + 1) % interval == 0) && (i != nx - 1 && j != ny - 1))
                    _imageWriter.writePixel(i, j, java.awt.Color.WHITE);
            }
        }

    }

    /**
     * Check if is transparent
     *
     * @param l        vector from light
     * @param n        normal
     * @param geoPoint point
     * @return double
     */
    private double transparency(Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); //from point to light source

        //add eps
        Vector epsVector = n.scale((n.dotProduct(lightDirection) > 0) ? EPS : -EPS);
        Point3D geometryPoint = geoPoint.point.add(epsVector);
        Ray lightRay = new Ray(geometryPoint, lightDirection);
        List<GeoPoint> intersectionPoints =
                getSceneIntersections(lightRay, l.getPoint3D().distance2(geoPoint.point));

        double ktr = 1;
        for (GeoPoint gp : intersectionPoints)
            ktr *= gp.geometry.getMaterial().getKT();
        return ktr;
    }

    /**
     * get all scene intersections with ray
     *
     * @param ray       the ray
     * @param distance2 the max distance. if -1 then returns all.
     * @return list with intersections
     */
    private List<GeoPoint> getSceneIntersections(Ray ray, double distance2) {
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(ray);
        if (distance2 == -1)
            return intersections;
        List<GeoPoint> filteredIntersections = new LinkedList<>();
        for (GeoPoint point : intersections) {
            if (ray.getPoint3D().distance2(point.point) <= distance2) {
                filteredIntersections.add(point);
            }
        }
        return filteredIntersections;
    }

    /**
     * Get Beam around ray
     *
     * @param ray    the ray
     * @param normal normal of the object
     * @param radius radius of the top of the beam
     * @param num    number of rays in beam
     * @return list of rays (beam)
     */
    public List<Ray> getBeam(Ray ray, Vector normal, double radius, int num) {
        Vector middle = ray.getVector();

        //check that the ray and the normal are at the same side
        if(middle.dotProduct(normal)<0)
            normal = normal.scale(-1);

        //get a random vector not parallel to middle
        Vector[] vectors = {
                new Vector(0, 0, 1),
                new Vector(0, 1, 0),
                new Vector(1, 0, 0),
                new Vector(0, 0, -1),
                new Vector(0, -1, 0),
                new Vector(-1, 0, 0)};
        Vector randV = new Vector(middle);
        int i = 0;
        do {
            try {
                randV = randV.add(vectors[i++]).normal();
            } catch (Exception ignored) { }
        } while (Math.abs(middle.dotProduct(randV)) == 1.0);

        //get two orthogonal vectors to middle
        Vector xV = middle.crossProduct(randV).normal();
        Vector yV = middle.crossProduct(xV).normal();

        //get vectors
        Random rand = new Random();
        List<Ray> beam = new LinkedList<>();
        beam.add(new Ray(ray));
        Point3D start = ray.getPoint3D();
        Point3D middleP = middle.getPoint3D();
        Point3D temp;
        do {
            try {
                temp = new Point3D(middleP);

                //get two random numbers in a circle with radius - 'radius'
                double xS;
                double yS;
                do {
                    xS = -radius + rand.nextDouble() * 2 * radius;
                    yS = -radius + rand.nextDouble() * 2 * radius;
                } while (xS * xS + yS * yS > radius * radius);

                //add the points
                temp = temp.add(xV.scale(xS));
                temp = temp.add(yV.scale(yS));

                //get the vector
                Vector v = new Vector(temp);

                //if it isn't under the geometry the add
                if (v.dotProduct(normal) > 0)
                    beam.add(new Ray(start, v));
            } catch (Exception ignored) { }
        } while (beam.size() < num);
        return beam;
    }
}

