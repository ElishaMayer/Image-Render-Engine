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
    /**
     * Check load render from xml
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    @Test
    public void checkLoadXml() throws IOException, SAXException, ParserConfigurationException {

        Render render= null;
        render = loadScene.loadFromXML("xml\\sceneTest");
        if(render == null)
            fail("render is null");
        if(render.getImageWriter()==null)
            fail("image writer is null");
        if(render.getScene()==null)
            fail("scene is null");
        if(render.getScene().getCamera()==null)
            fail("fail loading camera");
        if(render.getScene().getLight()==null)
            fail("fail loading light");
        if(render.getScene().getGeometries()==null)
            fail("fail loading geometries");
        if(render.getScene().getGeometries().getGeometries().size()!=8)
            fail("fail loading geometries");
      }
}