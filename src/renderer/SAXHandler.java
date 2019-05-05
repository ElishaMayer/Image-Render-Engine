package renderer;
import elements.AmbientLight;
import elements.Camera;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Sphere;
import geometries.Triangle;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class SAXHandler extends DefaultHandler {
    List<Geometries> _geometries = new ArrayList<>();
    Geometry _geometry = null;
    Scene _scene = null;
    String _content = null;
    ImageWriter _imageWriter =null;

    //Triggered when the start of tag is found.
    @Override
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes)
            throws SAXException {

        switch(qName){
            //Create a new Employee object when the start tag is found
            case "scene": {
                _scene = new Scene(attributes.getValue("name"));
                String color = attributes.getValue("background-color");
                double[] colors = parse3Numbers(color);
                _scene.setBackground(new Color(colors));
                int width = Integer.parseInt(attributes.getValue("screen-width"));
                int heigth = Integer.parseInt(attributes.getValue("screen-height"));
                _imageWriter = new ImageWriter(_scene.getName(), width, heigth, width, heigth);
            }
                break;
            case "ambient-light":
                double[] colors = parse3Numbers(attributes.getValue("color"));
                _scene.setLight(new AmbientLight(new Color(colors),Double.parseDouble(attributes.getValue("ka"))));
                break;
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
            case "sphere": {
                double[] points = parse3Numbers(attributes.getValue("center"));
                Point3D center = new Point3D(points[0], points[1], points[2]);
                Sphere sp = new Sphere(Double.parseDouble(attributes.getValue("radius")), center);
                _scene.addGeometries(sp);
            }
                break;
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

        }
    }

    private double[] parse3Numbers(String s){
        String[] numbers = s.split(" ");
        double[] num = new double[3];
        num[0] = Double.parseDouble(numbers[0]);
        num[1] = Double.parseDouble(numbers[1]);
        num[2] = Double.parseDouble(numbers[2]);
        return num;
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        _content = String.copyValueOf(ch, start, length).trim();
    }

}

class Employee {

    String id;
    String firstName;
    String lastName;
    String location;

    @Override
    public String toString() {
        return firstName + " " + lastName + "(" + id + ")" + location;
    }
}
