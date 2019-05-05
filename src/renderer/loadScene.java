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

public class loadScene {
    public static Render loadFromXML(String fileName) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        SAXHandler handler = new SAXHandler();
        String path = System.getProperty("user.dir")+"\\" + fileName + ".xml";
        File xmlFile = new File(path);
        InputStream fileReader = new FileInputStream(xmlFile);

        parser.parse(fileReader, handler);

        return new Render(handler._imageWriter,handler._scene);
}}
