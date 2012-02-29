package com.danieloskarsson.tv.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Daniel Oskarsson (daniel.oskarsson@gmail.com)
 */
public class Program {
    
	public Program() {}
	
    private String name;
    private Date start;
    private Date stop;

    public Program(String name, String start, String stop) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss Z");
        this.start = dateFormat.parse(start);
        this.stop = dateFormat.parse(stop);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Date getStart() {
        return start;
    }

    public Date getStop() {
        return stop;
    }

    @Override
    public String toString() {
        return "Program{" + "name=" + name + ", start=" + start + ", stop=" + stop + '}';
    }    
}
