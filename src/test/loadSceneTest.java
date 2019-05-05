package test;

import org.junit.Test;
import org.xml.sax.SAXException;
import renderer.Render;
import renderer.loadScene;
import scene.Scene;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.Assert.*;

public class loadSceneTest {
    @Test
    public void checkLoadXml() throws IOException, SAXException, ParserConfigurationException {

        Render render = loadScene.loadFromXML("scene");
    }
}