package com.danieloskarsson.tv.custom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CustomTvGuideRowComparator implements Comparator<CustomTvGuideRow> {
	
	private static List<String> list = new ArrayList<String>();

	public static void setChannels(String[] channels) {
		list = Arrays.asList(channels);
	}

	public int compare(CustomTvGuideRow lhs, CustomTvGuideRow rhs) {
		if (lhs.getStartDate().before(rhs.getStartDate())) return -1;
		else if (lhs.getStartDate().after(rhs.getStartDate())) return 1;
		
		if (list.indexOf(lhs.getChannel()) < list.indexOf(rhs.getChannel())) return -1;
		if (list.indexOf(lhs.getChannel()) > list.indexOf(rhs.getChannel())) return 1;
		
		return 0;
	}

}
