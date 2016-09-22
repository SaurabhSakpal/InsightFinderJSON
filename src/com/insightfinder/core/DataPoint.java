package com.insightfinder.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataPoint implements JSONObject {
	private String timestamp;
	private Long time;
	private String average;
	private String unit;
	
	
	public Long getTime() {
		return time;
	}
	
	public void setTime(Long time) {
		this.time = time;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getAverage() {
		return average;
	}
	
	public void setAverage(String average) {
		this.average = average;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Override
	public String toString() {
		StringBuilder objString = new StringBuilder("");
		objString.append(" { Average : ");
		objString.append(this.getAverage());
		objString.append("    TimeStamp : ");
		objString.append(this.getTime());
		objString.append("    Units : ");
		objString.append(this.getUnit());
		objString.append("    }");
		return objString.toString();
		
	}
	
	
	public static String getValue(String input) {
		String arr[] = input.split(":");
		String value = arr[1].replace('"', ' ');
		value = value.replace('}', ' ');
		value = value.replace(']', ' ');
		value = value.replace(',', ' ');
		value = value.trim();
		return value;
	}
	
	private String getTimestampValue(String input) {
		String pattern = "(\\d){4}[-](\\d){2}[-](\\d){2}[T](\\d){2}[:](\\d){2}[:](\\d){2}[Z]";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(input);
		String value = "";
		if(m.find()) {
			value = m.group(0);
		}
		return value;
	}
	
	@Override
	public List<DataPoint> JSONToList(String[] input_arr) {
		List<DataPoint> list = new LinkedList<DataPoint>();
		String timestamp = "";
		String average = "";
		String unit = "";
		int k = 0;
		for(int i = 0;i < input_arr.length; i++ ) {
			if(input_arr[i].indexOf("Timestamp") != -1) {
				timestamp = input_arr[i].substring(input_arr[i].indexOf("Timestamp"));
				timestamp = getTimestampValue(timestamp);
			}
			else if(input_arr[i].indexOf("Average") != -1) {
				average = input_arr[i].substring(input_arr[i].indexOf("Average"));
				average = getValue(average);
			}
			else if(input_arr[i].indexOf("Unit") != -1) {
				unit = input_arr[i].substring(input_arr[i].indexOf("Unit"));
				unit = getValue(unit);
			}
			k++;
			if(k % 3 == 0) {
				DataPoint triplet = new DataPoint();
				triplet.setAverage(average);
				triplet.setTimestamp(timestamp);
				
		        try {
		        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
			        String dateInString = timestamp.replaceAll("Z$", "+0000");
			        Date date = formatter.parse(dateInString);
			        triplet.setTime(date.getTime());
		        }
		        catch (Exception e) {
		        	System.out.println("Cannot Parse Date Properly " + e);
		        }
				triplet.setUnit(unit);
				timestamp = "";
				unit = "";
				average = "";
				list.add(triplet);
			}		
		}
		return list;
	}

}
