package com.miniproject;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import geometries.*;
import org.junit.Test;
import org.xml.sax.SAXException;
import primitives.*;
import renderer.ImageWriter;
import renderer.Render;
import renderer.RenderController;
import renderer.loadScene;
import scene.Scene;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static junit.framework.TestCase.fail;

/**
 * Home Work 3.
 * Written by:
 * Elisha Mayer ,319185997 ,elisja.mayer@gmail.com .
 * Menachem Natanson , 207134859, menachem.natanson@gmail.com
 */
public class Main {
    public static void main(String[] args) {
        proTestsNew();
    }

    /**
     * Render all tests in folder Tests
     */
    @Test
    public void renderTests() throws IOException, SAXException, ParserConfigurationException {
        System.out.println("-----Tests render-----");
        File folder = new File("xml\\Tests");
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                Render render = loadScene.loadFromXML("xml\\Tests\\" + listOfFiles[i].getName(),false);
                RenderController renderController = new RenderController(render.getImageWriter(),render.getScene(),render.getImageWriter().getGrid());
                renderController.renderImage();
                System.out.println("Successfully rendered picture: " + render.getImageWriter().getImageName());
                System.out.println("Completed: " + (int)(((double) i+1) / listOfFiles.length * 100.0) + "%");
            }
        }
    }


    public static void proTestsNew(){
        Scene scene = new Scene("");
        scene.setCamera(new Camera(new Point3D(-400,-1800,2000),new Vector(0,-1,0),new Vector(0,0,-1)),500);
        scene.getCamera().rotateXYZ(0,0,30);
        scene.setBackground(Color.BLACK);
        scene.setLight(new AmbientLight(new Color(20,20,20),1));
        scene.addLight(new DirectionalLight(new Color(200,200,180),new Vector(0,1,-1)));
        //scene.addLight(new SpotLight(new Color(655,655,655),new Point3D(200,-400,0),0.05,0.00005,0.000008,new Vector(5,1,-1)));
        Cube cube=		new Cube(
                new Square(
                        new Point3D(600,200,-400),
                        new Point3D(-1400,200,-400),
                        new Point3D(-1400,200,-2320),
                        new Point3D(600,200,-2320)),
                new Point3D(0,-2000,1000),
                50,
                new Material(0.5,0.5,100,0.7,0)
                ,new Color(0,0,0)) ;
        cube.setOptimised(true);
        scene.addGeometries(cube);
        Cylinder c1 = (new Cylinder(25,new Point3D(600,225,-400), new Point3D(-1400,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
        Cylinder c2 = (new Cylinder(25,new Point3D(600,225,-400), new Point3D(600,225,-2320),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
        Cylinder c3 = (new Cylinder(25,new Point3D(-1400,225,-2320), new Point3D(-1400,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
        Sphere sp1 = (new Sphere(25,new Point3D(600,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
        Sphere sp2 = (new Sphere(25,new Point3D(-1400,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
        //	scene.addGeometries(new Plane(new Point3D(670,225,-400),new Vector(1,0,0),new Material(0.5,0.5,100,0.8,0,0.015,0),new Color(20,20,20)));
        //	scene.addGeometries(new Plane(new Point3D(-1470,225,-400),new Vector(1,0,0),new Material(0.5,0.5,100,0.8,0,0.015,0),new Color(20,20,20)));
        //	scene.addGeometries(new Plane(new Point3D(-1400,225,-2390),new Vector(0,0,1),new Material(0,0,100,0,0),new Color(0,0,0)));
        Square sq1 = (new Square(new Point3D(670,200,200),new Point3D(670,200,-2320),new Point3D(670,-1000,-2320),new Point3D(670,-1000,200),new Material(0.1,0.1,100,0.67,0,0.01,0),new Color(20,20,20)));
        Square sq2 = (new Square(new Point3D(-1470,200,200),new Point3D(-1470,200,-2320),new Point3D(-1470,-1000,-2320),new Point3D(-1470,-1000,200),new Material(0.1,0.1,100,0.67,0,0.01,0),new Color(20,20,20)));
        c1.setOptimised(true);
        c2.setOptimised(true);
        c3.setOptimised(true);
        sp1.setOptimised(true);
        sp2.setOptimised(true);
        sq1.setOptimised(true);
        sq2.setOptimised(true);
        scene.addGeometries(c1,c2,c3,sp1,sp2,sq1,sq2);


        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                if(!((j>=4&&j<=5)&&(i>=4&&i<=5)) ){
                    Sphere sp = new Sphere(100, new Point3D(500 - i * 200, 100, -(500 + j * 200)), new Material(0.5, 0.5, 1000, 0.2, 0), new Color(Math.random() * 100, Math.random() * 100, Math.random() * 100));
                    sp.setOptimised(true);
                    scene.addGeometries(sp);
                }
            }
        }
        Sphere sp = new Sphere(400,new Point3D(500-900,-300,-(500+900)),new Material(0.1,0.5,1000,0.65,0),new Color(	10,10,10));
        sp.setOptimised(true);
        scene.addGeometries(sp);
        ImageWriter imw = new ImageWriter("images\\IMG_0051_NewBalls",500,300,2000,1200);
        RenderController rn = new RenderController(imw,scene);
        rn.renderImage();
    }
}
