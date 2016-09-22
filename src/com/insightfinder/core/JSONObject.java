package com.insightfinder.core;

import java.util.List;

public interface JSONObject <T extends JSONObject> {
	public List<T> JSONToList(String input[]);
}
