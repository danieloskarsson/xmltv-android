package com.danieloskarsson.tv.grabber;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.NodeList;

import android.util.Log;

import com.danieloskarsson.tv.model.Channel;
import com.danieloskarsson.tv.model.Program;

/**
 *
 * @author Daniel Oskarsson (daniel.oskarsson@gmail.com)
 */
class ChannelsFactory extends AbstractXpathFactory {
    
    private List<Channel> channels = new ArrayList<Channel>();

    public ChannelsFactory(String xmlString, List<String> whitelist) throws IOException {
        super(xmlString);
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(Calendar.getInstance().getTime());
        
        try {
            XPathExpression expression = xpath.compile("/tv/channel/@id");
            NodeList ids = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
            expression = xpath.compile("/tv/channel/display-name/text()");
            NodeList names = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
                        
            int j = 1;
            for (int i = 0; i < ids.getLength(); i++) {
                String id = ids.item(i).getNodeValue();
                String name = names.item(i).getNodeValue();
                if (!whitelist.contains(id)) continue; // Only fetch programs for white listed channels
                
                Log.d("downloading...", String.format("%d/%d", j++, whitelist.size()));
                
                List<Program> programs = Grabber.grabPrograms(id, today);
                Channel channel = new Channel(id, name);
                channel.addPrograms(Calendar.getInstance().getTime(), programs);
                channels.add(channel);
            }
        
        } catch (XPathExpressionException ex) {
        	Log.e(ChannelsFactory.class.getCanonicalName(), ex.getMessage());
        }
    }

    public List<Channel> getChannels() {
        return channels;
    }    
}
