package test;

import elements.AmbientLight;
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
	/**
	 * Render Scene with 4 Triangles and a circle
	 */
	@Test
	public void basicRendering(){
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, 1)),150);
		scene.setBackground(new Color(0, 0, 0));
		scene.setLight(new AmbientLight(new Color(255,255,255),1.0));
		scene.addGeometries(new Sphere( 50, new Point3D(0, 0, 150)));

        scene.addGeometries(new Triangle(new Point3D( 100, 0, 150),
				 							new Point3D(  0, 100, 150),
				 							new Point3D( 100, 100, 150)));

        scene.addGeometries(new Triangle( new Point3D( 100, 0, 150),
				 			 				new Point3D(  0, -100, 150),
				 			 				new Point3D( 100,-100, 150)));

        scene.addGeometries(new Triangle( new Point3D(-100, 0, 150),
				 							new Point3D(  0, 100, 150),
				 							new Point3D(-100, 100, 150)));

        scene.addGeometries(new Triangle( new Point3D(-100, 0, 150),
				 			 				new Point3D(  0,  -100, 150),
				 			 				new Point3D(-100, -100, 150)));

		ImageWriter imageWriter = new ImageWriter("images\\IMG_0004_Scene", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.printGrid(50);
		imageWriter.writeToimage();
	}

	/**
	 * Render cylinder standing
	 */
	@Test
	public void cylinderRendering1(){
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, 1)),150);
		scene.setBackground(new Color(0, 0, 0));
		scene.setLight(new AmbientLight(new Color(255,255,255),1.0));

		scene.addGeometries(new Cylinder(50,new Ray(new Point3D( 0, 0, 150),
				new Vector(  0, 1, 0)),
				100));



		ImageWriter imageWriter = new ImageWriter("images\\IMG_0005_Cylinder1", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.printGrid(50);
		imageWriter.writeToimage();
	}

	/**
	 * Render lying cylinder
	 */
	@Test
	public void cylinderRendering2(){
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, 1)),150);
		scene.setBackground(new Color(0, 0, 0));
		scene.setLight(new AmbientLight(new Color(255,255,255),1.0));

		scene.addGeometries(new Cylinder(50,new Ray(new Point3D( 0, 0, 150),
				new Vector(  0, 0, 1)),
				100));


		ImageWriter imageWriter = new ImageWriter("images\\IMG_0006_Cylinder2", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.printGrid(50);
		imageWriter.writeToimage();
	}

	/**
	 * Render standing Tube
	 */
	@Test
	public void tubeRendering1(){
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, 1)),150);
		scene.setBackground(new Color(0, 0, 0));
		scene.setLight(new AmbientLight(new Color(255,255,255),1.0));

		scene.addGeometries(new Tube(50,new Ray(new Point3D( 0, 0, 150),
				new Vector(  0, 1, 0))));



		ImageWriter imageWriter = new ImageWriter("images\\IMG_0007_Tube1", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.printGrid(50);
		imageWriter.writeToimage();
	}

	/**
	 * Render lying Tube
	 */
	@Test
	public void tubeRendering2(){
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, 1)),150);
		scene.setBackground(new Color(0, 0, 0));
		scene.setLight(new AmbientLight(new Color(255,255,255),1.0));

		scene.addGeometries(new Tube(50,new Ray(new Point3D( 0, 0, 150),
				new Vector(  0, 0, 1))));


		ImageWriter imageWriter = new ImageWriter("images\\IMG_0008_Tube2", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.printGrid(50);
		imageWriter.writeToimage();
	}

	/**
	 * Render from xml scene
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	@Test
	public void fromXmlRendering() throws IOException, SAXException, ParserConfigurationException {
		Render render = loadScene.loadFromXML("xml\\scene");
		render.renderImage();
		render.printGrid(50);
		render.getImageWriter().writeToimage();
	}
}