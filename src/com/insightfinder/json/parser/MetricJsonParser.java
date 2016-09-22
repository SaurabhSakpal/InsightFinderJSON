package com.insightfinder.json.parser;

import java.util.LinkedList;
import java.util.List;

import com.insightfinder.core.JSONObject;

public class MetricJsonParser <T extends JSONObject> implements JsonParser {
	
	public static String getValue(String input) {
		String arr[] = input.split(":");
		arr[1] = arr[1].trim();
		String value = arr[1];
		int indexOfComma = value.indexOf(","); 
		if(indexOfComma != -1) {
			value = value.substring(0, indexOfComma);
		}
		value = value.replace('"',' ');
		value = value.trim();
		return value;
	}
	
	public List<T> parse(String s, String delimiter, Class c) {
		List<T> listOfObj= new LinkedList<T>();
		String arr[] = s.split(delimiter);
		for(int i = 0 ;i<arr.length;i++) {
			arr[i] = arr[i].trim();
		}
		try {
			JSONObject obj = (JSONObject) c.newInstance();
			listOfObj = obj.JSONToList(arr);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return listOfObj;
	}


}
