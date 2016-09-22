package com.insightfinder.json.fetcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class JSONFetcher {
	
	public static String readJSONFromURL(String url) throws IOException, MalformedURLException {
		URL oracle = new URL(url);
		BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
		String inputLine;
		String jsonString = "";
		while ((inputLine = in.readLine()) != null)
			jsonString = jsonString + inputLine + "\n";
		in.close();
		return jsonString;
	}

}
