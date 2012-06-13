package com.danieloskarsson.tv;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;

import com.danieloskarsson.tv.custom.CustomTvGuide;
import com.danieloskarsson.tv.grabber.Grabber;
import com.danieloskarsson.tv.model.Channel;
import com.danieloskarsson.tv.model.Program;

/**
 * 
 * @author Daniel Oskarsson (daniel.oskarsson@gmail.com)
 */
final class ContentTask extends AsyncTask<String, Void, String> {

	private static Map<String, List<Channel>> channels = new HashMap<String, List<Channel>>();
	private String template;
	private WebView webView;
	
	public ContentTask(String template, WebView webView) {
		this.template = template; // onPostExecute will merge template and generated html
		this.webView = webView; // Get reference to UI thread, so we don't have to push this code into TVActivity
	}
	
	@Override
	protected void onPostExecute(String html) {
		String data = template.replace("%html%", html);
		webView.loadData(data, "text/html; charset=utf-8", null); // Executed on the UI thread
	}

	@Override
	protected String doInBackground(String... args) {
		List<String> whitelist = Arrays.asList(args); // The interface to doInBackground want's varargs...		
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		timeFormat.setTimeZone(TimeZone.getTimeZone("GMT+1"));

		// Fetch filtered channels from Map or from Xmltv
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String today = dateFormat.format(Calendar.getInstance().getTime());
		List<Channel> channels = ContentTask.channels.get(today);
		if (channels == null) {
			try {
				channels = new Grabber(whitelist).getChannels();
				ContentTask.channels.put(today, channels);
			} catch (IOException e) {
				Log.e(ContentTask.class.getCanonicalName(), e.getMessage());
				return "error";
			}
		}
		
		// Iterate channels and programs and create custom html representation
		CustomTvGuide customTvGuide = new CustomTvGuide();
		for (Channel channel : channels) {
			List<Program> programs = channel.getPrograms();
			for (Program program : programs) {
				customTvGuide.addProgram(program, channel.getId());
			}
		}
		return customTvGuide.asHtml();
	}
}
