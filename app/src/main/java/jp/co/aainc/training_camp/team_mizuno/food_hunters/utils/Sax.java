package jp.co.aainc.training_camp.team_mizuno.food_hunters.utils;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Sax {

    public static void parse(InputStream inputStream, DefaultHandler handler) {
        try {
            newParser().parse(inputStream, handler);
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SAXParser newParser() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            return factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
