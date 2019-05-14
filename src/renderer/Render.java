package renderer;

import primitives.Point3D;
import primitives.Ray;
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
        for(int i=0;i<_imageWriter.getWidth();i++){
            for(int j=0;j<_imageWriter.getHeight();j++){
                Ray ray = _scene.getCamera().constructRayThroughPixel(_imageWriter.getNx(),_imageWriter.getNy(),i,j,
                        _scene.getCameraDistance(),_imageWriter.getWidth(),_imageWriter.getHeight());
                Point3D intersection = getClosestPoint(_scene.getGeometries().findIntersections(ray));
                if(intersection == null)
                    _imageWriter.writePixel(i,j,_scene.getBackground().getColor());
                else{
                    _imageWriter.writePixel(i,j,calcColor(intersection));
                }
            }
        }

    }

    private Color calcColor(Point3D p){
        return _scene.getLight().GetIntensity().getColor();
    }

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

    public ImageWriter getImageWriter() {
        return _imageWriter;
    }

    public Scene getScene() {
        return _scene;
    }

    public void printGrid(int interval){
        for(int i=0;i<_imageWriter.getWidth();i++){
            for(int j=0;j<_imageWriter.getHeight();j++){
                if((i+1)%interval == 0 || (j+1)%interval == 0)
                    _imageWriter.writePixel(i,j,Color.WHITE);
            }
        }

    }

}

