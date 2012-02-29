package com.danieloskarsson.tv.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Daniel Oskarsson (daniel.oskarsson@gmail.com)
 */
public class Channel {
	
	public Channel() {}
    
    private String id;
    private String name;
    private String logo = "http://xmltv.tvsajten.com/chanlogos/%s.png";
    
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Map<String, List<Program>> allPrograms = new HashMap<String, List<Program>>();

    public Channel(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public String getLogo() {
        return String.format(logo, id);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
    
	public List<Program> getPrograms() {
		return getPrograms(Calendar.getInstance().getTime(), DisplayMode.FUTURE);
    }
    
    public List<Program> getPrograms(Date date) {
    	return getPrograms(date, DisplayMode.FUTURE);
    }
    
    public List<Program> getPrograms(Date date, DisplayMode programMode) {
        String string = dateFormat.format(date);
        List<Program> datePrograms = allPrograms.get(string);
        if (datePrograms == null) datePrograms = new ArrayList<Program>();
        
        if (DisplayMode.ALL.equals(programMode)) return datePrograms;
        List<Program> programModePrograms = new ArrayList<Program>();
        for (Program program : datePrograms) {
        	if (program.getStop().after(date)) {
        		if (DisplayMode.CURRENT.equals(programMode)) {
        			if (program.getStart().before(date)) {
                		programModePrograms.add(program);
        			}        			
        		} else {
            		programModePrograms.add(program);
        		}
        	}	
		}
        return programModePrograms;    	
    }
    
    public void addPrograms(Date date, List<Program> programs) {
        String string = dateFormat.format(date);
        allPrograms.put(string, programs);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Channel other = (Channel) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }
    
	@Override
    public String toString() {
        return "Channel{" + "id=" + id + ", name=" + name + ", logo=" + getLogo() + '}';
    }
}
