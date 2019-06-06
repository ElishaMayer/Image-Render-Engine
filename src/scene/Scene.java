package scene;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Geometries;
import geometries.Geometry;
import primitives.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * A Scene with geometries ,camera and light
 */
public class Scene {
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
    public void addGeometries(Geometry... geometries) {
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
}
