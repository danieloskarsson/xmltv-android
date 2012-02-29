package com.danieloskarsson.tv.grabber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Daniel Oskarsson (daniel.oskarsson@gmail.com)
 */
class Curl {
    public static String getFrom(String string) throws IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(string);
		HttpResponse execute = client.execute(httpGet);
		InputStream inputStream = execute.getEntity().getContent();
        GZIPInputStream gzip = new GZIPInputStream(inputStream);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gzip, Charset.forName("ISO-8859-1")));
        
		String xml = "", inputLine; 
        while ((inputLine = bufferedReader.readLine()) != null) {
            xml += inputLine;     
        }
        bufferedReader.close();	
        inputStream.close();
        gzip.close();
        return xml;
    }
}
