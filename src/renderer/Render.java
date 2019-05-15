package renderer;

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
        for(int i=0;i<_imageWriter.getWidth();i++){
            for(int j=0;j<_imageWriter.getHeight();j++){
                // get all the rays who goes through every pixel and see if they intersects any geometries
                Ray ray = _scene.getCamera().constructRayThroughPixel(_imageWriter.getNx(),_imageWriter.getNy(),i,j,
                        _scene.getCameraDistance(),_imageWriter.getWidth(),_imageWriter.getHeight());
                Point3D intersection = getClosestPoint(_scene.getGeometries().findIntersections(ray));
                if(intersection == null)
                    _imageWriter.writePixel(i,j,_scene.getBackground().getColor()); // no intersection = background color
                else{
                    _imageWriter.writePixel(i,j,calcColor(intersection)); // intersection = calculate the right color
                }
            }
        }
    }

    /**
     * calculate the color of a point in the scene
     *
     * @param p the point which we calculate the color from
     * @return the color of the requested point
     */
    private Color calcColor(Point3D p){
        return _scene.getLight().GetIntensity().getColor();
    }

    /**
     * find the point with the minimal distance from the camera from the given intersection points list
     *
     * @param list the intersection points with a ray in the scene
     * @return the closet point to the camera, or null if there are no intersection points
     */
    private Point3D getClosestPoint(List<Point3D> list){
        Point3D p = _scene.getCamera().getP0();
        if(list.size()==0)
            return null;
        if(list.size()==1)
            return list.get(0);
        Point3D pToReturn = list.get(0);
        for (Point3D point:list) {
            if(p.distance2(point)<p.distance2(pToReturn))
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
        for(int i=0;i<_imageWriter.getWidth();i++){
            for(int j=0;j<_imageWriter.getHeight();j++){
                if((i+1)%interval == 0 || (j+1)%interval == 0)
                    _imageWriter.writePixel(i,j,Color.WHITE);
            }
        }

    }

}

