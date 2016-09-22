package com.insightfinder.json.parser;

import java.util.LinkedList;
import java.util.List;

import com.insightfinder.core.JSONObject;



public class GenericJSONParser <T extends JSONObject> implements JsonParser {
	
	@Override
	public List<T> parse(String input, String delimiter, Class c) {
		List<T> listOfObj= new LinkedList<T>();
		String input_arr[] = input.split(delimiter);
		try {
			JSONObject obj = (JSONObject) c.newInstance();
			listOfObj = obj.JSONToList(input_arr);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return listOfObj;
	}

}
