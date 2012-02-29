package com.danieloskarsson.tv.grabber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.danieloskarsson.tv.model.Channel;
import com.danieloskarsson.tv.model.Program;

/**
 *
 * @author Daniel Oskarsson (daniel.oskarsson@gmail.com)
 */
public class Grabber {
    
    private static final String URL_CHANNELS = "http://tv.swedb.se/xmltv/channels.xml.gz";
    private static final String URL_TEMPLATE = "http://xmltv.tvsajten.com/xmltv/%s_%s.xml.gz";
    
    private List<Channel> channels = new ArrayList<Channel>();
    
    public Grabber(List<String> whitelist) throws IOException {
        String channelsXml = Curl.getFrom(URL_CHANNELS);
        ChannelsFactory channelsFactory = new ChannelsFactory(channelsXml, whitelist);
        channels = channelsFactory.getChannels();
    }

    static List<Program> grabPrograms(String channelId, String date) throws IOException {
        String url = String.format(URL_TEMPLATE, channelId, date);
        String programsXml = Curl.getFrom(url);
        programsXml = programsXml.replace("<!DOCTYPE tv SYSTEM \"xmltv.dtd\">", "");
        ProgramsFactory programsFactory = new ProgramsFactory(programsXml);
        List<Program> programs = programsFactory.getPrograms();
        return programs;
    }

    public List<Channel> getChannels() {
        return channels;
    }
    
}
