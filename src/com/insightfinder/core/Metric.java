package com.insightfinder.core;

import java.util.LinkedList;
import java.util.List;

public class Metric implements JSONObject{
	private String namespace;
	private Dimension dimensions;
	private String metricName;
	
	public String getNamespace() {
		return namespace;
	}
	
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	public Dimension getDimensions() {
		return dimensions;
	}
	
	public void setDimensions(Dimension dimensions) {
		this.dimensions = dimensions;
	}
	
	public String getMetricName() {
		return metricName;
	}
	
	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}
	
	@Override
	public String toString() {
		StringBuilder objString = new StringBuilder("");
		objString.append(" { MetricName : ");
		objString.append(this.getMetricName());
		objString.append("    NameSpace : ");
		objString.append(this.getNamespace());
		objString.append("    Dimension : ");
		objString.append(this.getDimensions().toString());
		objString.append("    }");
		return objString.toString();	
	}
	
	public static String getMetricValue(String input) {
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
	
	@Override
	public List<Metric> JSONToList(String input[]) {
		boolean flag = false;
		LinkedList<Metric> list = new LinkedList<Metric>();
		Dimension d = new Dimension();
		String namespace = "";
		String name = "";
		String value = "";
		String metricName = "";
		Metric m = new Metric();
		for (int i = 0; i<input.length; i++) {
			if (input[i].indexOf("}") != -1 && i != input.length - 1) {
				if (flag) {
					d.setName(name);
					d.setValue(value);
					flag = false;
				}
				else {
					m.setDimensions(d);
					m.setMetricName(metricName);
					m.setNamespace(namespace);
					list.add(m);
					
					d = new Dimension();
					name = "";
					value = "";
					metricName = "";
					namespace = "";
					m = new Metric();
				}
			}
			else if (input[i].indexOf("Dimensions") != -1) {
				flag = true;
			}
			else if (input[i].indexOf("Namespace") != -1) {
				namespace = getMetricValue(input[i]);
			}
			else if (input[i].indexOf("MetricName") != -1) {
				metricName = getMetricValue(input[i]);
			}
			else if (input[i].indexOf("Name") != -1) {
				name = getMetricValue(input[i]);
			}
			else if (input[i].indexOf("Value") != -1) {
				value = getMetricValue(input[i]);
			}
		}
		return list;
	}
}
