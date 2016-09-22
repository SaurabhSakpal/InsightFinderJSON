package com.insightfinder.core;

import java.util.HashMap;

/**
 * CombinedMetricDataPoint is used to store the average values for each possible metric at given @timestamp
 * @metricMap is used to store the mapping of MetricName to its average value at that @timestamp
 * 
 * @author saurabh
 * 
 */

public class CombinedMetricDataPoint implements Comparable<CombinedMetricDataPoint>  {
	
	private long timestamp;

	public HashMap<String, SingleMetricDataPoint> metricMap = new HashMap<String, SingleMetricDataPoint>(); 

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public int compareTo(CombinedMetricDataPoint obj) {
		if (this.timestamp > obj.timestamp) {
			return 1;
		}
		else if (this.timestamp < obj.timestamp) {
			return -1;
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.timestamp == ((CombinedMetricDataPoint)obj).timestamp;
	}
	
	@Override
	public String toString() {
		StringBuilder objString = new StringBuilder("");
		objString.append(" { Timestamp : ");
		objString.append(this.getTimestamp());
		for (String metricName : this.metricMap.keySet()) {
			objString.append("  ");
			objString.append(metricName);
			objString.append(" : ");
			objString.append(this.metricMap.get(metricName).getAverage());
		}
		return objString.toString();		
	}
}
