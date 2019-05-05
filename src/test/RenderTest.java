package test;

import org.junit.Test;

import elements.Camera;
import geometries.*;
import org.xml.sax.SAXException;
import primitives.*;
import renderer.ImageWriter;
import renderer.Render;
import renderer.loadScene;
import scene.Scene;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class RenderTest {
	@Test
	public void basicRendering(){
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, 1)),100);
		scene.setBackground(new Color(0, 0, 0));

		scene.addGeometries(new Sphere( 50, new Point3D(0, 0, 150)));

        scene.addGeometries(new Triangle(new Point3D( 100, 0, 149),
				 							new Point3D(  0, 100, 149),
				 							new Point3D( 100, 100, 149)));

        scene.addGeometries(new Triangle( new Point3D( 100, 0, 149),
				 			 				new Point3D(  0, -100, 149),
				 			 				new Point3D( 100,-100, 149)));

        scene.addGeometries(new Triangle( new Point3D(-100, 0, 149),
				 							new Point3D(  0, 100, 149),
				 							new Point3D(-100, 100, 149)));

        scene.addGeometries(new Triangle( new Point3D(-100, 0, 149),
				 			 				new Point3D(  0,  -100, 149),
				 			 				new Point3D(-100, -100, 149)));

		ImageWriter imageWriter = new ImageWriter("test0", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.printGrid(50);
		imageWriter.writeToimage();
	}

	@Test
	public void fromXmlRendering() throws IOException, SAXException, ParserConfigurationException {
		Render render = loadScene.loadFromXML("scene");
		render.renderImage();
		render.printGrid(50);
		render.getImageWriter().writeToimage();
	}
}