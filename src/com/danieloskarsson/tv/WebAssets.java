package com.danieloskarsson.tv;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.util.Log;

/**
 * 
 * @author Daniel Oskarsson (daniel.oskarsson@gmail.com)
 */
final class WebAssets {
	private AssetManager assetManager;
	
	public WebAssets(AssetManager assetManager) {
		this.assetManager = assetManager;
	}
	
    /**
     * This method returns a html template that contains the magic string %html%.
     * The purpose is to replace %html% with generated html
     * while still being able to edit the 'frame' around that data as in an asset.
     * @return A string representation of the template asset.
     */
    public String getTemplate() {
    	try {
			InputStream inputStream = assetManager.open("template.html");
			byte[] buffer = new byte[inputStream.available()];
			inputStream.read(buffer);
			inputStream.close();
			return new String(buffer).replace("<link rel='stylesheet' type='text/css' href='file:///android_asset/stylesheet.css' />", getStyleSheet());
		} catch (IOException e) {
			Log.e(TVActivity.class.getCanonicalName(), "Could not read asset template.html");
		}
    	return "<html>%html%</html>";
    }
    
    /**
     * This method is used by {@link #getTemplate()} to dynamically replace the link to the asset style sheet.
     * The reason for doing that is that WebView does not seem to parse asset references.
     * This way the same style sheet can be used by both the template and the about asset.
     * @return A string representation of the style sheet asset.
     */
    public String getStyleSheet() {
    	try {
			InputStream inputStream = assetManager.open("stylesheet.css");
			byte[] buffer = new byte[inputStream.available()];
			inputStream.read(buffer);
			inputStream.close();
			StringBuilder stylesheet = new StringBuilder();
			stylesheet.append("<style type='text/css'>");
			stylesheet.append(new String(buffer));
			stylesheet.append("</style>");
			return stylesheet.toString();
		} catch (IOException e) {
			Log.e(TVActivity.class.getCanonicalName(), "Could not read asset stylesheet.css");
		}
    	return "<style type='text/css'><style>";
    }
}
