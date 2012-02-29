package com.danieloskarsson.tv.grabber;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.util.Log;

/**
 *
 * @author Daniel Oskarsson (daniel.oskarsson@gmail.com)
 */
abstract class AbstractXpathFactory {

    protected Document document;
    protected XPath xpath;

    public AbstractXpathFactory(String xml) throws IOException {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            InputStream xmlStream = new ByteArrayInputStream(xml.getBytes("ISO-8859-1"));
            document = builder.parse(xmlStream);
            
            XPathFactory factory = XPathFactory.newInstance();
            xpath = factory.newXPath();
        } catch (ParserConfigurationException ex) {
        	Log.e(AbstractXpathFactory.class.getCanonicalName(), ex.getMessage());
        } catch (SAXException ex) {
        	Log.e(AbstractXpathFactory.class.getCanonicalName(), ex.getMessage());
        }

    }
    
}
