package renderer;

import elements.Camera;
import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.awt.*;
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
     * @param imageWriter
     * @param scene
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

        for(int i=0;i<nx;i++){
            for(int j=0;j<ny;j++){
                // get all the rays who goes through every pixel and see if they intersects any geometries
                Ray ray = camera.constructRayThroughPixel(nx,ny,i,j,distance,width,height);
                Intersectable.GeoPoint intersection = getClosestPoint(geometries.findIntersections(ray));
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
        primitives.Color color =  _scene.getLight().getIntensity();
        color = color.add(geopoint.geometry.getEmission());

        Vector v = geopoint.point.subtract(_scene.getCamera().getP0()).normal();
        Vector n = geopoint.geometry.getNormal(geopoint.point);
        int nShininess = geopoint.geometry.getMaterial().getNShininess();
        double kd = geopoint.geometry.getMaterial().getKD();
        double ks = geopoint.geometry.getMaterial().getKS();
        for (LightSource lightSource : _scene.getLights()) {
            Vector l = lightSource.getL(geopoint.point);
            if (n.dotProduct(l) * n.dotProduct(v) > 0) {
                primitives.Color lightIntensity = lightSource.getIntensity(geopoint.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }

        return color.getColor();
    }

    /**
     * calculate the Diffusive factor
     *
     * @param kd
     * @param l
     * @param n
     * @param lightIntensity
     * @return
     */
    private primitives.Color calcDiffusive(double kd, Vector l, Vector n, primitives.Color lightIntensity) {
        // kd∙|l∙n|∙lightIntensity
        return lightIntensity.scale(kd*Math.abs(l.dotProduct(n)));
    }

    /**
     * calculate the Specular factor
     *
     * @param ks
     * @param l
     * @param n
     * @param v
     * @param nShininess
     * @param lightIntensity
     * @return
     */
    private primitives.Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, primitives.Color lightIntensity) {
        Vector r = l.subtract(n.scale(2*l.dotProduct(n))).normal(); // r = l - 2∙(l∙n)∙𝒏

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
}

