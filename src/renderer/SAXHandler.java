package renderer;
import elements.AmbientLight;
import elements.Camera;
import geometries.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;

/**
 * a handler for xml extract
 */
public class SAXHandler extends DefaultHandler {
    //variables
    Scene _scene = null;
    ImageWriter _imageWriter =null;
    String version=null;

    //Triggered when the start of tag is found.
    @Override
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes) {

        switch(qName){
            //Create new scene
            case "scene": {
                _scene = new Scene(attributes.getValue("name"));
                version = attributes.getValue("version");
                String color = attributes.getValue("background-color");
                double[] colors = parse3Numbers(color);
                _scene.setBackground(new Color(colors));
                int width = Integer.parseInt(attributes.getValue("screen-width"));
                int heigth = Integer.parseInt(attributes.getValue("screen-height"));
                _imageWriter = new ImageWriter(_scene.getName(), width, heigth, width, heigth);
            }
                break;
            //Create new light
            case "ambient-light":
                double[] colors = parse3Numbers(attributes.getValue("color"));
                _scene.setLight(new AmbientLight(new Color(colors),Double.parseDouble(attributes.getValue("ka"))));
                break;
            //Create new camera
            case "camera": {
                double[] points = parse3Numbers(attributes.getValue("p0"));
                Point3D p0 = new Point3D(points[0], points[1], points[2]);
                points = parse3Numbers(attributes.getValue("vTo"));
                Vector vTo = new Vector(points[0], points[1], points[2]);
                points = parse3Numbers(attributes.getValue("vUp"));
                Vector vUp = new Vector(points[0], points[1], points[2]);
                Camera cam = new Camera(p0, vUp, vTo);
                _scene.setCamera(cam, Double.parseDouble(attributes.getValue("Screen-dist")));
            }
                break;
            //Create new sphere
            case "sphere": {
                double[] points = parse3Numbers(attributes.getValue("center"));
                Point3D center = new Point3D(points[0], points[1], points[2]);
                Sphere sp = new Sphere(Double.parseDouble(attributes.getValue("radius")), center);
                _scene.addGeometries(sp);
            }
                break;
            //Create new triangle
            case "triangle":
            {
                double[] points = parse3Numbers(attributes.getValue("p0"));
                Point3D p0 = new Point3D(points[0], points[1], points[2]);
                points = parse3Numbers(attributes.getValue("p1"));
                Point3D p1 = new Point3D(points[0], points[1], points[2]);
                points = parse3Numbers(attributes.getValue("p2"));
                Point3D p2 = new Point3D(points[0], points[1], points[2]);
                _scene.addGeometries(new Triangle(p0,p1,p2));
            }
                break;
            //create new cylinder
            case "cylinder":
            {
                double[] points = parse3Numbers(attributes.getValue("Ray-p"));
                Point3D rayP = new Point3D(points[0], points[1], points[2]);
                points = parse3Numbers(attributes.getValue("Ray-v"));
                Vector rayV = new Vector(points[0], points[1], points[2]);
                double radius = Double.parseDouble(attributes.getValue("radius"));
                double heigth = Double.parseDouble(attributes.getValue("heigth"));
                _scene.addGeometries(new Cylinder(radius,new Ray(rayP,rayV),heigth));
            }
            break;
            //create new tube
            case "tube":
            {
                double[] points = parse3Numbers(attributes.getValue("Ray-p"));
                Point3D rayP = new Point3D(points[0], points[1], points[2]);
                points = parse3Numbers(attributes.getValue("Ray-v"));
                Vector rayV = new Vector(points[0], points[1], points[2]);
                double radius = Double.parseDouble(attributes.getValue("radius"));
                _scene.addGeometries(new Tube(radius,new Ray(rayP,rayV)));
            }
            break;
            //create new plane
            case "plane":
            {
                double[] points = parse3Numbers(attributes.getValue("p"));
                Point3D p = new Point3D(points[0], points[1], points[2]);
                points = parse3Numbers(attributes.getValue("v"));
                Vector v = new Vector(points[0], points[1], points[2]);
                _scene.addGeometries(new Plane(p,v));
            }
            break;

        }
    }

    /**
     * parse 3 number from string
     * @param s string
     * @return the 3 numbers
     */
    private double[] parse3Numbers(String s){
        String[] numbers = s.split(" ");
        double[] num = new double[3];
        num[0] = Double.parseDouble(numbers[0]);
        num[1] = Double.parseDouble(numbers[1]);
        num[2] = Double.parseDouble(numbers[2]);
        return num;
    }
}


