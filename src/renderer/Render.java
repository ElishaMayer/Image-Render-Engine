package renderer;

import elements.Camera;
import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;
import geometries.Sphere;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Render class (makes a bitmap picture from the scene)
 */
public class Render {
    // variables
    private ImageWriter _imageWriter;
    private Scene _scene;

    /* ********* Constructors ***********/

    /**
     * a new Render
     * @param imageWriter image writer
     * @param scene scene
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        _imageWriter = imageWriter;
        _scene = scene;
    }

    /* ************* Getters/Setters *******/

    /**
     * Get image writer
     * @return image writer
     */
    public ImageWriter getImageWriter() {
        return _imageWriter;
    }

    /**
     * Get scene
     * @return scene
     */
    public Scene getScene() {
        return _scene;
    }

    /* ************* Operations ***************/

    /**
     * render the scene into the imageWriter
     */
    public void renderImage(){

        //variables
        int nx = _imageWriter.getNx();
        int ny = _imageWriter.getNy();
        double width = _imageWriter.getWidth();
        double height = _imageWriter.getHeight();
        Camera camera = _scene.getCamera();
        double distance = _scene.getCameraDistance();
        Geometries geometries = _scene.getGeometries();
        Color background = _scene.getBackground().getColor();

        //render image
        for(int i=0;i<nx;i++){
            for(int j=0;j<ny;j++){
                // get all the rays who goes through every pixel and see if they intersects any geometries
                Ray ray = camera.constructRayThroughPixel(nx,ny,i,j,distance,width,height);
                Intersectable.GeoPoint intersection = getClosestPoint(getSceneIntersections(ray,-1));
                if(intersection == null)
                    _imageWriter.writePixel(i,j,background); // no intersection = background color
                else{
                    _imageWriter.writePixel(i,j,calcColor(intersection, ray)); // intersection = calculate the right color
                }
            }
        }
    }


    private Color calcColor(Intersectable.GeoPoint geopoint, Ray inRay) {
        return calcColor(geopoint, inRay, 5, 1.0).add(_scene.getLight().getIntensity()).getColor();
    }

    private static final double MIN_CALC_COLOR_K = 0.001;
    /**
     * calculate the color of a point in the scene
     *
     * @param geopoint the point which we calculate the color from
     * @return the color of the requested point
     */
    private primitives.Color calcColor(Intersectable.GeoPoint geopoint, Ray inRay, int level, double k){
        if(level == 0 || k < MIN_CALC_COLOR_K) return primitives.Color.BLACK;

        //add emission
        primitives.Color color = geopoint.geometry.getEmission();

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
                if(ktr * k > MIN_CALC_COLOR_K) {
                    //add light
                    primitives.Color lightIntensity = lightSource.getIntensity(geopoint.point).scale(ktr);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }

        // Recursive call for a reflected ray
        double kr = geopoint.geometry.getMaterial().getKR();
        Vector newV = inRay.getVector();
        Vector direction;
        // reflectedRay vector = v-2∙(v∙n)∙n
        direction = newV.subtract(n.scale(2 * newV.dotProduct(n)));
        Ray reflectedRay = new Ray(geopoint.point.add(direction), direction);
        Intersectable.GeoPoint reflectedPoint = getClosestPoint(getSceneIntersections(reflectedRay,-1));
        if (reflectedPoint != null)
            color = color.add(calcColor(reflectedPoint, reflectedRay, level-1, k*kr).scale(kr));

        // Recursive call for a refracted ray
        double kt = geopoint.geometry.getMaterial().getKT();
        direction = newV;
        Ray refractedRay = new Ray(geopoint.point.add(direction), direction) ;
        Intersectable.GeoPoint refractedPoint = getClosestPoint(getSceneIntersections(refractedRay,-1));
        if (refractedPoint != null)
            color = color.add(calcColor(refractedPoint, refractedRay , level-1, k*kt).scale(kt));

        return color;
    }

    /**
     * calculate the Diffusive factor
     *
     * @param kd kd
     * @param l vector from light
     * @param n normal
     * @param lightIntensity light intensity
     * @return diffusive light
     */
    private primitives.Color calcDiffusive(double kd, Vector l, Vector n, primitives.Color lightIntensity) {
        // kd∙|l∙n|∙lightIntensity
        return lightIntensity.scale(kd*Math.abs(l.dotProduct(n)));
    }

    /**
     * calculate the Specular factor
     *
     * @param ks ks
     * @param l vector from light
     * @param n normal
     * @param v vector v
     * @param nShininess material shininess
     * @param lightIntensity light intensity
     * @return specular light
     */
    private primitives.Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, primitives.Color lightIntensity) {
      Vector r;
      //get vector r
      try {
          r = l.subtract(n.scale(2 * l.dotProduct(n))).normal(); // r = l - 2∙(l∙n)∙𝒏
      }catch(Exception ex){
          r=l;
        }
        // ks∙(max(0,-v∙r))^nShininess∙lightIntensity
        return lightIntensity.scale(ks*Math.pow(Math.max(0,v.scale(-1).dotProduct(r)),nShininess));
    }

    /**
     * find the point with the minimal distance from the camera from the given intersection points list
     *
     * @param list the intersection points with a ray in the scene
     * @return the closet point to the camera, or null if there are no intersection points
     */
    private Intersectable.GeoPoint getClosestPoint(List<Intersectable.GeoPoint> list){
        Point3D p = _scene.getCamera().getP0();
        if(list.size()==0)
            return null;
        if(list.size()==1)
            return list.get(0);
        Intersectable.GeoPoint pToReturn = list.get(0);
        for (Intersectable.GeoPoint point:list) {
            if(p.distance2(point.point)<p.distance2(pToReturn.point))
                pToReturn = point;
        }
        return pToReturn;
    }

    /**
     * add a grid on top of the image writer
     *
     * @param interval the distance between the lines of the grid
     */
    public void printGrid(int interval){
        int nx = _imageWriter.getNx();
        int ny = _imageWriter.getNy();
        for(int i=0;i<nx;i++){
            for(int j=0;j<ny;j++){
                if(((i+1)%interval == 0 || (j+1)%interval == 0)&&(i!=nx-1&&j!=ny-1))
                    _imageWriter.writePixel(i,j,Color.WHITE);
            }
        }

    }

    /**
     * Check if is transparent
     * @param l vector from light
     * @param n normal
     * @param geoPoint point
     * @return double
     */
    private double transparency(Vector l, Vector n, Intersectable.GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); //from point to light source

        Vector epsVector = n.scale((n.dotProduct(lightDirection) > 0) ? 2 : -2);
        Point3D geometryPoint = geoPoint.point.add(epsVector);
        Ray lightRay = new Ray(geometryPoint, lightDirection);
        List<Intersectable.GeoPoint> intersectionPoints  =
                getSceneIntersections(lightRay,l.getPoint3D().distance2(geoPoint.point));

        double ktr = 1;
        for (Intersectable.GeoPoint gp : intersectionPoints)
            ktr *= gp.geometry.getMaterial().getKT();
        return ktr;
    }

    /**
     * get all scene intersections with ray
     * @param ray the ray
     * @param distance2 the max distance. if -1 then returns all.
     * @return list with intersections
     */
    private List<Intersectable.GeoPoint> getSceneIntersections(Ray ray,double distance2){
        List<Intersectable.GeoPoint> intersections = _scene.getGeometries().findIntersections(ray);
        if(distance2 == -1)
            return intersections;
        List<Intersectable.GeoPoint> filteredIntersections=new LinkedList<>();
        for (Intersectable.GeoPoint point:intersections) {
            if(ray.getPoint3D().distance2(point.point)<=distance2){
                filteredIntersections.add(point);
            }
        }
        return filteredIntersections;
    }

    public List<Ray> getBeam(Ray ray, Vector normal,double radius,int num){
        Vector vectors[] = {
                new Vector(0,0,1),
                new Vector(0,1,0),
                new Vector(1,0,0),
                new Vector(0,0,-1),
                new Vector(0,-1,0),
                new Vector(-1,0,0)};
        Vector middle = ray.getVector();
        Vector v= new Vector(middle);
        int i=0;
        do {
            try {
                v = v.add(vectors[i++]).normal();
            }catch (Exception ex){}
        }while (Math.abs(middle.dotProduct(v))==1.0);
        Vector x = middle.crossProduct(v).normal();
        Vector y = middle.crossProduct(x).normal();

        Random rand = new Random();
        List<Ray> beam  = new LinkedList<>();
        do {
            try {
                Vector temp = new Vector(middle);
                double xS = 0;
                double yS = 0;
                do {
                    xS = -radius + rand.nextDouble() * 2 * radius;
                    yS = -radius + rand.nextDouble() * 2 * radius;
                }while (xS*xS+yS*yS>radius*radius);
                temp = x.scale(xS).subtract(temp);
                temp = y.scale(yS).subtract(temp);
                if(temp.dotProduct(normal)>0)
                    beam.add(new Ray(ray.getPoint3D(),temp));
            }catch (Exception ex){}
        }while (beam.size()<num);
        return beam;
    }
}

