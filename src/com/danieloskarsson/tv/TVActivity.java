package com.danieloskarsson.tv;

import com.danieloskarsson.tv.custom.CustomTvGuideRowComparator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;

/**
 * 
 * @author Daniel Oskarsson (daniel.oskarsson@gmail.com)
 */
public class TVActivity extends Activity {

	private static String[] channels = new String[]{"svt1.svt.se", "svt2.svt.se", "tv3.viasat.se", "tv4.se", "kanal5.se", "tv6.viasat.se"};
	
	static {
		CustomTvGuideRowComparator.setChannels(channels);
	}
	
	private WebAssets webAssets;
	private WebView webView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        webAssets = new WebAssets(getAssets());
        webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl("file:///android_asset/loading.html");
		
        // This will perform a task in the background and when done update the UI using the reference to webView
        ContentTask contentTask = new ContentTask(webAssets.getTemplate(), webView);
        contentTask.execute(channels);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.tv, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    		case R.id.refresh:
    	        webView.loadUrl("file:///android_asset/loading.html");
    	        
    	        // This will perform a task in the background and when done update the UI using the reference to webView
    	        ContentTask contentTask = new ContentTask(webAssets.getTemplate(), webView);
    	        contentTask.execute(channels);
    			return true;
    		case R.id.about:
    			webView.loadUrl("file:///android_asset/about.html");
    			return true;
    			/*
    		case R.id.preferences:      
    			showHelp();       
    			return true;
    			*/
    		default:       
    			return super.onOptionsItemSelected(item);
    	}
    }
}