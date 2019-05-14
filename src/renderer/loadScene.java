package renderer;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import scene.Scene;

/**
 * Load data from xml
 */
public class loadScene {
    /**
     * Load a render object from xml
     *
     * @param fileName the xml file name
     * @return a render object with the data
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static Render loadFromXML(String fileName) throws ParserConfigurationException, SAXException, IOException {

        //load the xml into the handler
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        SAXHandler handler = new SAXHandler();
        String path = System.getProperty("user.dir") + "\\" + fileName + ".xml";
        File xmlFile = new File(path);
        InputStream fileReader = new FileInputStream(xmlFile);
        parser.parse(fileReader, handler);

        if(!handler.version.equals("1.0"))
            throw new IllegalArgumentException("Wrong xml version");
        return new Render(handler._imageWriter, handler._scene);
    }
}
