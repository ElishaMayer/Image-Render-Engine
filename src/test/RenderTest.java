package test;

import elements.AmbientLight;
import elements.DirectionalLight;
import elements.SpotLight;
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
import java.io.File;
import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.fail;

public class RenderTest {

	/**
	 * Render all tests in folder Exc6
	 *
	 */
	@Test
	public void renderEx6() {
		String message = "";
		System.out.println("-----Ex6 render-----");
		File folder = new File("xml\\Exc6");
		File[] listOfFiles = folder.listFiles();
		assert listOfFiles != null;
		for (int i = 0; i < listOfFiles.length; i++) {
			try {
				if (listOfFiles[i].isFile()) {
					Render render = loadScene.loadFromXML("xml\\Exc6\\" + listOfFiles[i].getName(),false);
					render.renderImage();
					if(render.getImageWriter().getGrid())
						render.printGrid(50);
					render.getImageWriter().writeToimage();
					System.out.println("Successfully rendered picture: " + render.getImageWriter().getImageName());
					System.out.println("Completed: " + (int)(((double) i+1) / listOfFiles.length * 100.0) + "%");
				}
			} catch (Exception ex) {
				message = "\n" + "Error in: " + listOfFiles[i].getName()+"\n"+"Error details: " + ex.toString();
			}
		}
		if(message != ""){
			fail(message);
		}
	}

	/**
	 * Render all tests in folder Exc5

	 */
	@Test
	public void renderEx5() {
		String message ="";
		System.out.println("-----Ex5 render-----");
		File folder = new File("xml\\Exc5");
		File[] listOfFiles = folder.listFiles();
		assert listOfFiles != null;
		for (int i = 0; i < listOfFiles.length; i++) {
			try {
				if (listOfFiles[i].isFile()) {
					Render render = loadScene.loadFromXML("xml\\Exc5\\" + listOfFiles[i].getName(),true);
					render.renderImage();
					if(render.getImageWriter().getGrid())
						render.printGrid(50);
					render.getImageWriter().writeToimage();
					System.out.println("Successfully rendered picture: " + render.getImageWriter().getImageName());
					System.out.println("Completed: " + (int)(((double) i+1) / listOfFiles.length * 100.0) + "%");
				}
			} catch (Exception ex) {
				message = "\n" + "Error in: " + listOfFiles[i].getName()+"\n"+"Error details: " + ex.toString();
			}
		}
		if(message != ""){
			fail(message);
		}
	}

	/**
	 * Render all tests in folder Exc7

	 */
	@Test
	public void renderEx7() {
		String message ="";
		System.out.println("-----Ex7 render-----");
		File folder = new File("xml\\Exc7");
		File[] listOfFiles = folder.listFiles();
		assert listOfFiles != null;
		for (int i = 0; i < listOfFiles.length; i++) {
			try {
				if (listOfFiles[i].isFile()) {
					Render render = loadScene.loadFromXML("xml\\Exc7\\" + listOfFiles[i].getName(),false);
					render.renderImage();
					if(render.getImageWriter().getGrid())
						render.printGrid(50);
					render.getImageWriter().writeToimage();
					System.out.println("Successfully rendered picture: " + render.getImageWriter().getImageName());
					System.out.println("Completed: " + (int)(((double) i+1) / listOfFiles.length * 100.0) + "%");
				}
			} catch (Exception ex) {
				message = "\n" + "Error in: " + listOfFiles[i].getName()+"\n"+"Error details: " + ex.toString();
			}
		}
		if(message != ""){
			fail(message);
		}
	}

	/**
	 * Render all tests in folder Tests
	 */
	@Test
	public void renderTests() throws IOException, SAXException, ParserConfigurationException {
		String message ="";
		System.out.println("-----Tests render-----");
		File folder = new File("xml\\Tests");
		File[] listOfFiles = folder.listFiles();
		assert listOfFiles != null;
		for (int i = 0; i < listOfFiles.length; i++) {
			//	try {
			if (listOfFiles[i].isFile()) {
				Render render = loadScene.loadFromXML("xml\\Tests\\" + listOfFiles[i].getName(),false);
				render.renderImage();
				if(render.getImageWriter().getGrid())
					render.printGrid(50);
				render.getImageWriter().writeToimage();
				System.out.println("Successfully rendered picture: " + render.getImageWriter().getImageName());
				System.out.println("Completed: " + (int)(((double) i+1) / listOfFiles.length * 100.0) + "%");
			}
			//	} catch (Exception ex) {
			//		message = "\n" + "Error in: " + listOfFiles[i].getName()+"\n"+"Error details: " + ex.toString();
			//	}
		}
		if(message != ""){
			fail(message);
		}
	}


	/**
	 * 100 Balls on plane
	 */
	@Test
	public void proTests(){
		Scene scene = new Scene("");
		scene.setCamera(new Camera(new Point3D(-400,-1800,2000),new Vector(0,-1,0),new Vector(0,0,-1)),500);
		scene.getCamera().rotateXYZ(0,0,30);
		scene.setBackground(Color.BLACK);
		scene.setLight(new AmbientLight(new Color(20,20,20),1));
		scene.addLight(new DirectionalLight(new Color(200,200,180),new Vector(0,1,-1)));
		//scene.addLight(new SpotLight(new Color(655,655,655),new Point3D(200,-400,0),0.05,0.00005,0.000008,new Vector(5,1,-1)));
		scene.addGeometries(
				new Cube(
						new Square(
								new Point3D(600,200,-400),
								new Point3D(-1400,200,-400),
								new Point3D(-1400,200,-2320),
								new Point3D(600,200,-2320)),
						new Point3D(0,-2000,1000),
						50,
						new Material(0.5,0.5,100,0.7,0)
						,new Color(0,0,0)) );
		scene.addGeometries(new Cylinder(25,new Point3D(600,225,-400), new Point3D(-1400,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		scene.addGeometries(new Cylinder(25,new Point3D(600,225,-400), new Point3D(600,225,-2320),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		scene.addGeometries(new Cylinder(25,new Point3D(-1400,225,-2320), new Point3D(-1400,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		scene.addGeometries(new Sphere(25,new Point3D(600,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		scene.addGeometries(new Sphere(25,new Point3D(-1400,225,-400),new Material(0.5,0.5,100,0,0),new Color(0,0,0)));
		scene.addGeometries(new Plane(new Point3D(670,225,-400),new Vector(1,0,0),new Material(0.5,0.5,100,0.8,0),new Color(20,20,20)));
		scene.addGeometries(new Plane(new Point3D(-1470,225,-400),new Vector(1,0,0),new Material(0.5,0.5,100,0.8,0),new Color(20,20,20)));
		scene.addGeometries(new Plane(new Point3D(-1400,225,-2390),new Vector(0,0,1),new Material(0,0,100,0,0),new Color(0,0,0)));

		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				scene.addGeometries(new Sphere(100,new Point3D(500-i*200,100,-(500+j*200)),new Material(0.5,0.5,1000,0.2,0),new Color(	Math.random()*100,Math.random()*100,Math.random()*100)));
			}
		}
		ImageWriter imw = new ImageWriter("images\\IMG_0021_Balls",500,500,2000,2000);
		Render rn = new Render(imw,scene);
		rn.renderImage();
		imw.writeToimage();
	}

	@Test
	public void testBeam(){
		Vector v = new Vector(1,1,1);
		v=v.normal();
		Vector n = new Vector(0,0,1);
		Render rn = new Render(null,null);
		List<Ray> beam = rn.getBeam(new Ray(new Point3D(0,0,0),v),n,0.002,10);
		for (Ray r:beam){
			if(v.getPoint3D().distance(r.getVector().getPoint3D())>0.2)
				fail();
		}

	}
}







