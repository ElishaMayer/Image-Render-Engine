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

import static junit.framework.TestCase.fail;

public class RenderTest {

	@Test
	public void renderEx6() throws IOException, SAXException, ParserConfigurationException {
		String message = "";
		System.out.println("-----Ex6 render-----");
		File folder = new File("xml\\Exc6");
		File[] listOfFiles = folder.listFiles();
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
		if(message!=""){
			fail(message);
		}
	}

	@Test
	public void renderEx5() throws IOException, SAXException, ParserConfigurationException {
		String message ="";
		System.out.println("-----Ex5 render-----");
		File folder = new File("xml\\Exc5");
		File[] listOfFiles = folder.listFiles();
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
		if(message!=""){
			fail(message);
		}
	}

	@Test
	public void renderTests() throws IOException, SAXException, ParserConfigurationException {
		String message ="";
		System.out.println("-----Tests render-----");
		File folder = new File("xml\\Tests");
		File[] listOfFiles = folder.listFiles();
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
		if(message!=""){
			fail(message);
		}
	}

	@Test
	public void proTests(){
		Scene scene = new Scene("");
		scene.setCamera(new Camera(new Point3D(-400,-600,800),new Vector(0,-1,0),new Vector(0,0,-1)),250);
		scene.setBackground(Color.BLACK);
		scene.setLight(new AmbientLight(new Color(20,20,20),1));
		scene.addLight(new DirectionalLight(new Color(150,150,130),new Vector(-1,1,-1)));
		scene.addLight(new SpotLight(new Color(655,655,655),new Point3D(200,-400,0),0.05,0.00005,0.000008,new Vector(5,1,-1)));
		scene.addGeometries(new Triangle(new Point3D(600,0,-400),new Point3D(-1400,0,-400),new Point3D(600,-1000,-2320),new Material(0.5,0.5,100),new Color(0,0,0)));
		scene.addGeometries(new Triangle(new Point3D(-1400,0,-400),new Point3D(600,-1000,-2320),new Point3D(-1400,-1000,-2320),new Material(0.5,0.5,100),new Color(0,0,0)));

		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				scene.addGeometries(new Sphere(100,new Point3D(500-i*200,-(100+j*100),-(500+j*180)),new Material(0.5,0.5,100),new Color(	Math.random()*100,Math.random()*100,Math.random()*100)));
			}
		}
		ImageWriter imw = new ImageWriter("images\\IMG_0021_Balls",500,500,2000,2000);
		Render rn = new Render(imw,scene);
		rn.renderImage();
		imw.writeToimage();
	}

}







