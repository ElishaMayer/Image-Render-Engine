package scene;

import elements.AmbientLight;
import elements.Camera;
import geometries.Geometries;
import geometries.Geometry;
import primitives.Color;

public class Scene {
private String _name;
private Color _background;
private AmbientLight _light;
private Geometries _geometries;
private Camera _camera;
private double _cameraDistance;
public Scene(String name){
    _name = name;
    _geometries = new Geometries();
}

    public String getName() {
        return _name;
    }

    public Color getBackground() {
        return _background;
    }

    public AmbientLight getLight() {
        return _light;
    }

    public Geometries getGeometries() {
        return _geometries;
    }

    public Camera getCamera() {
        return _camera;
    }

    public double getCameraDistance() {
        return _cameraDistance;
    }

    public void setBackground(Color background) {
        _background = background;
    }

    public void setLight(AmbientLight light) {
        _light = light;
    }

    public void setCamera(Camera camera , double cameraDistance) {
        _camera = camera;
        _cameraDistance = cameraDistance;
    }

    public void addGeometries(Geometry...geometries){
        _geometries.add(geometries);
    }
}
