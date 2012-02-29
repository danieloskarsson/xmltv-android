package com.danieloskarsson.tv.grabber;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.NodeList;

import android.util.Log;

import com.danieloskarsson.tv.model.Program;

/**
 *
 * @author Daniel Oskarsson (daniel.oskarsson@gmail.com)
 */
class ProgramsFactory extends AbstractXpathFactory {
    
    private List<Program> programs = new ArrayList<Program>();
    
    public ProgramsFactory(String xml) throws IOException {
        super(xml);
        
        try {
            XPathExpression expression = xpath.compile("/tv/programme/@start");
            NodeList starts = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
            expression = xpath.compile("/tv/programme/@stop");
            NodeList stops = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
            expression = xpath.compile("tv/programme/title/text()");
            NodeList names = (NodeList) expression.evaluate(document, XPathConstants.NODESET);

            for (int i = 0; i < names.getLength(); i++) {
                String name = names.item(i).getNodeValue();
                String start = starts.item(i).getNodeValue();
                String stop = stops.item(i).getNodeValue();
                
                Program program;
                try {
                    program = new Program(name, start, stop);
                    programs.add(program);
                } catch (ParseException ex) {
                	Log.e(ProgramsFactory.class.getCanonicalName(), ex.getMessage());
                }
            }        
        
        } catch (XPathExpressionException ex) {
        	Log.e(ProgramsFactory.class.getCanonicalName(), ex.getMessage());
        }
    }

    public List<Program> getPrograms() {
        return programs;
    }
}
