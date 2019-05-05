package renderer;

import primitives.Point3D;
import scene.Scene;

import java.awt.*;
import java.util.List;

public class Render {
    private ImageWriter _imageWriter;
    private Scene _scene;

    public Render(ImageWriter imageWriter, Scene scene) {
        _imageWriter = imageWriter;
        _scene = scene;
    }

    public void renderImage(){

    }

    private Color calcColor(Point3D p){
        return null;
    }

    private Point3D getClosestPoint(List<Point3D> points){
        return null;
    }

    public void printGrid(int interval){

    }

}

