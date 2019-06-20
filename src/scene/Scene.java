package scene;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Box;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

/**
 * A Scene with geometries ,camera and light
 */
public class Scene {
    private static final int START_DISTANCE = 15;
    private static final int TREE_DEPTH = 10;
    private static final double EMPTY_VOLUME = 0.75;
    //variables
    private String _name;
    private Color _background;
    private AmbientLight _light;
    private Geometries _geometries;
    private Camera _camera;
    private double _cameraDistance;
    private List<LightSource> _lights;

    /* ********* Constructors ***********/
    /**
     * A new Scene
     * @param name Scene name
     */
    public Scene(String name) {
        _name = name;
        _geometries = new Geometries();
        _lights = new ArrayList<>();
    }

    /* ************* Getters/Setters *******/
    /**
     * get scene name
     * @return name
     */
    public String getName() {
        return _name;
    }

    /**
     * get scene background
     * @return background
     */
    public Color getBackground() {
        return _background;
    }

    /**
     * get scene light
     * @return light
     */
    public AmbientLight getAmbient() {
        return _light;
    }


    /**
     * get scene geometries
     * @return geometries
     */
    public Geometries getGeometries() {
        return _geometries;
    }

    /**
     * get scene camera
     * @return camera
     */
    public Camera getCamera() {
        return _camera;
    }

    /**
     * get camera distance
     * @return camera distance
     */
    public double getCameraDistance() {
        return _cameraDistance;
    }

    /**
     * get light sources
     * @return list of light sources
     */
    public List<LightSource> getLights() {
        return _lights;
    }

    /**
     * set scene background
     * @param background the background color
     */
    public void setBackground(Color background) {
        _background = background;
    }

    /**
     * set scene light
     * @param light the light
     */
    public void setLight(AmbientLight light) {
        _light = light;
    }

    /**
     * set scene camera and distance
     * @param camera the camera
     * @param cameraDistance camera distance
     */
    public void setCamera(Camera camera, double cameraDistance) {
        _camera = camera;
        _cameraDistance = cameraDistance;
    }

    /**
     * set light sources
     * @param light list of light sources
     */
    public void addLight(LightSource light) {
        _lights.add(light);
    }

    /**
     * add one or more geometries
     * @param geometries
     */
    public void addGeometries(Intersectable... geometries) {
        _geometries.add(geometries);
    }

    /* ************** Admin *****************/
    @Override
    public String toString() {
        return "Scene{" +
                "Name='" + _name + '\'' +
                ", Background=" + _background +
                ", light=" + _light +
                ", geometries=" + _geometries +
                ", camera=" + _camera +
                ", Distance=" + _cameraDistance +
                '}';
    }

    public void buildBoxes(){
        Box box = new Box(_geometries.getGeometries());
        Point3D maxP = box.getMax();
        Point3D minP = box.getMin();
        Point3D difference = maxP.subtract(minP).getPoint3D();
        double distance = (abs(difference.getX().get())+ abs(difference.getY().get())+ abs(difference.getZ().get()));
        box = splitBoxes(box,distance/START_DISTANCE,distance);
        _geometries.getGeometries().clear();
        _geometries.add(box);
    }

    private Box splitBoxes(Box box,double distance,double maxDistance){
        if( box.getGeometries().size()==1)
            return box;
        int boxSize = box.getGeometries().size();
        distance*=TREE_DEPTH;
        List<Intersectable> newBoxList = new ArrayList<>();
        List<Intersectable> temp = new ArrayList<>();
        List<Intersectable> boxList = box.getGeometries();
        do {
            Intersectable first = boxList.get(0);
            temp.add(first);
            boxList.remove(first);
            for (int i=0;i<boxList.size();i++) {
                if(first.getMiddle().distance(boxList.get(i).getMiddle())<distance){
                    List<Intersectable> tempBox = new ArrayList<>(temp);
                    tempBox.add(boxList.get(i));
                    Box original = new Box(temp);
                    Box added = new Box(tempBox);
                    if((original.getVolume()+boxList.get(i).getVolume())/added.getVolume()>EMPTY_VOLUME) {
                        temp.add(boxList.get(i));
                        boxList.remove(boxList.get(i));
                    }
                }
            }
            if(temp.size()>1)
                newBoxList.add(new Box(temp));
            else
                newBoxList.add(first);
            temp.clear();
        }while (!boxList.isEmpty());
        Box returnBox = new Box(newBoxList);
        if(returnBox.getGeometries().size()==boxSize && distance>maxDistance)
            return returnBox;
        return splitBoxes(returnBox,distance,maxDistance);
    }
}
