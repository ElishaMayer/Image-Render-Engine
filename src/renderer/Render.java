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
                    _imageWriter.writePixel(i,j,calcColor(intersection)); // intersection = calculate the right color
                }
            }
        }
    }


    /**
     * calculate the color of a point in the scene
     *
     * @param geopoint the point which we calculate the color from
     * @return the color of the requested point
     */
    private Color calcColor(Intersectable.GeoPoint geopoint){
        //calc ambient light
        primitives.Color color =  _scene.getLight().getIntensity();
        //add emission
        color = color.add(geopoint.geometry.getEmission());

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
                if(!occluded(l,geopoint)) {
                    //add light
                    primitives.Color lightIntensity = lightSource.getIntensity(geopoint.point);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }

        return color.getColor();
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
     * Check if is occuled
     * @param l vector from light
     * @param geoPoint point
     * @return boolean
     */
    private boolean occluded(Vector l, Intersectable.GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); //from point to light source
        Vector normal = geoPoint.geometry.getNormal(geoPoint.point);
        Vector epsVector = normal.scale((normal.dotProduct(lightDirection) > 0) ? 2 : -2);
        Point3D geometryPoint = geoPoint.point.add(epsVector);
        Ray lightRay = new Ray(geometryPoint, lightDirection);
        List<Intersectable.GeoPoint> intersectionPoints  =
                getSceneIntersections(lightRay,l.getPoint3D().distance2(geoPoint.point));
        return  !intersectionPoints.isEmpty();
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


}

