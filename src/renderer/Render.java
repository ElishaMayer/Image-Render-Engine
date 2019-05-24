package renderer;

import elements.Camera;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Point3D;
import primitives.Ray;
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
     * @param intersection the point which we calculate the color from
     * @return the color of the requested point
     */
    private Color calcColor(Intersectable.GeoPoint intersection){
        primitives.Color color =  _scene.getLight().getIntensity();
        color = color.add(intersection.geometry.getEmission());
        return  color.getColor();
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
                if((i+1)%interval == 0 || (j+1)%interval == 0)
                    _imageWriter.writePixel(i,j,Color.WHITE);
            }
        }

    }

}

