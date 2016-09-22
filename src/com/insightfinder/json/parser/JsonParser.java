package com.insightfinder.json.parser;

import java.util.List;

import com.insightfinder.core.JSONObject;

public interface JsonParser <T extends JSONObject> {
	public List<T> parse(String input, String delimiter, Class <JSONObject> c);

}
