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
					Render render = loadScene.loadFromXML("xml\\Exc6\\" + listOfFiles[i].getName(),true);
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

}