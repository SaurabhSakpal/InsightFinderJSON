package com.insightfinder.core;

/**
 * Dimension is part of Metric class. 
 *
 * The JSON read from "http://thho.net/files/insightfinder/techtest/metrics.json" has following format.
 * Dimension has two member variables @Name and @Value
	 {
	      "Namespace": "AWS/EC2",
	      "Dimensions": [
	        {
	          "Name":"InstanceId",
	          "Value":"i-dd8855ba"
	        }
	      ],
	      "MetricName": "CPUUtilization"
	 }
 * 
 * @author saurabh
 *
 */


public class Dimension {
	
	private String name;
	private String value;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		StringBuilder objString = new StringBuilder("");
		objString.append(" { Name : ");
		objString.append(this.getName());
		objString.append("    Value : ");
		objString.append(this.getValue());
		objString.append("    }");
		return objString.toString();	
	}

}
