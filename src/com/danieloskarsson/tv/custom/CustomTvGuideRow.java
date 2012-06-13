package com.danieloskarsson.tv.custom;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomTvGuideRow {
	
	private String name;
	private Date start;
	private Date stop;
	private String channel;
	
	public CustomTvGuideRow(String name, Date start, Date stop, String channel) {
		super();
		this.name = name;
		this.start = start;
		this.stop = stop;
		this.channel = channel;
	}
	
	public Date getStartDate() {
		return start;
	}
	public long getLengthNumber() {
		long lengthInMilliseconds = stop.getTime() - start.getTime();
		return lengthInMilliseconds / 1000 / 60;
	}
	
	public String getStart() {
		return new SimpleDateFormat("HH:mm").format(start);
	}
	public String getName() {
		return name;
	}
	public String getLength() {
		return String.valueOf(getLengthNumber());
	}
	public String getChannel() {
		return channel;
	}
	public String getChannelShort() {
		int index = channel.indexOf(".");
		if (index >= 1) return (channel.substring(0,  index));
		return channel;
	}
}
