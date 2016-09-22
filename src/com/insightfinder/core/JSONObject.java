package com.insightfinder.core;

import java.util.List;

/**
 * Any class that needs to parse a JSON file to List of its instances should implement this interface  
 * @param <T> class that extends JSONObject interface
 * 
 * @author saurabh
 */
public interface JSONObject <T extends JSONObject> {
	public List<T> JSONToList(String input[]);
}
