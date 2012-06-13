package com.danieloskarsson.tv.custom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.danieloskarsson.tv.model.Program;

/**
 * The purpose of this class is to calculate and generate content according to:
 * 
 * A program is a first-class citizen.
 * Programs are NOT grouped by channel.
 * Programs are ordered according to their start time.
 * Programs are ordered according to the order of the selected channels.
 * Programs do not have an end time. They have a length.
 * Programs have attributes such as: length and channel.
 * 
 * An example:
 * 
 * 19:55
 * 19:00 The Real Housewvies of Atlanta (60 min) (tv3)
 * 19:30 The Bing Bang Theory (30 min) (kanal5)
 * 19:30 2 1/2 män (30 min) (tv6)
 * 19:30 Rapport (30 min) (svt1)
 * 19:30 Miffo-tv (30 min) (svt2)
 * 19:30 Halv åtta hos mig (30 min) (tv4)
 * 20:00 Tunnelbanan (60 min) (kanal5)
 * 20:00 Efterlyst (60 min) (tv3)
 * 20:00 Chuck (60 min) (tv6)
 * 20:00 Uppdrag granskning (60 min) (svt1)
 * 20:00 Den njutbara trädgården (30 min) (svt2)
 * 20:00 Sveriges mästerkock (60 min) (tv4)
 * 
 * The selected channels are (in order): kanal5, tv3, tv6, svt1, svt2, tv4 in the example above.
 * 
 * @author Daniel Oskarsson (daniel.oskarsson@gmail.com)
 *
 */
public class CustomTvGuide {
	
	private List<CustomTvGuideRow> rows = new ArrayList<CustomTvGuideRow>();

	public void addProgram(Program program, String channel) {
		rows.add(new CustomTvGuideRow(program.getName(), program.getStart(), program.getStop(), channel));
	}
	
	public String asHtml() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("<table>");
		stringBuilder.append("<tbody>");
		
		CustomTvGuideRow[] array = rows.toArray(new CustomTvGuideRow[0]);
		Arrays.sort(array, new CustomTvGuideRowComparator());
		
		for (CustomTvGuideRow row : array) {
			String start = row.getStart();
			String name = row.getName();
			String length = row.getLength();
			String channel = row.getChannelShort();			
			stringBuilder.append(String.format("<tr><td>%s</td><td>%s</td><td>(%s min)</td><td>(%s)</td></tr>", start, name, length, channel));
		}

		stringBuilder.append("</tbody>");
		stringBuilder.append("</table>");

		return stringBuilder.toString();
	}
}
