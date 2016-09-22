package com.insightfinder.main;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

import com.insightfinder.core.DataPoint;
import com.insightfinder.core.Metric;
import com.insightfinder.json.fetcher.JSONFetcher;
import com.insightfinder.json.parser.GenericJSONParser;

class HelloWorld implements Comparable<HelloWorld>  {
	
	private long timestamp;
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public HashMap<String, DataPoint> metricMap = new HashMap<String, DataPoint>(); 

	@Override
	public int compareTo(HelloWorld o) {
		if(this.timestamp > o.timestamp) {
			return 1;
		}
		else if (this.timestamp < o.timestamp) {
			return -1;
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.timestamp == ((HelloWorld)obj).timestamp;
	}
	
	@Override
	public String toString() {
		StringBuilder objString = new StringBuilder("");
		objString.append(" { Timestamp : ");
		objString.append(this.getTimestamp());
		for(String metricName : this.metricMap.keySet()) {
			objString.append("  ");
			objString.append(metricName);
			objString.append(" : ");
			objString.append(this.metricMap.get(metricName).getAverage());
		}
		return objString.toString();		
	}

	
}

public class MainExecutor {
	static final String URL_PART1 = "http://thho.net/files/insightfinder/techtest/";
	static final String URL_PART2 = ".json";
	static final String METRICS_URL = "http://thho.net/files/insightfinder/techtest/metrics.json";
	

	public static void main(String args[])throws Exception {
		
		String jsonString =  JSONFetcher.readJSONFromURL(METRICS_URL);
		GenericJSONParser<Metric> metricsJsonParser = new GenericJSONParser<Metric>();
		List <Metric> objectList = metricsJsonParser.parse(jsonString, "\n", Metric.class);
		ConcurrentSkipListSet<HelloWorld> skipList = new ConcurrentSkipListSet<HelloWorld>();
		for(Metric obj : objectList) {
			System.out.println(obj);
			String metricName = obj.getMetricName();
			jsonString =  JSONFetcher.readJSONFromURL(URL_PART1 +metricName + URL_PART2);
			GenericJSONParser<DataPoint> individualMetricJSONParser = new GenericJSONParser<DataPoint> ();
			List<DataPoint> list = individualMetricJSONParser.parse(jsonString, ",", DataPoint.class);
			int counter = 0;
			long minTimeStamp = list.get(0).getTime();
			while(counter < list.size()) {
				HelloWorld e = new HelloWorld();
				e.setTimestamp(minTimeStamp);
				if( minTimeStamp == list.get(counter).getTime()) {
					DataPoint obj_rep = list.get(counter);
					if(!skipList.add(e)) {
						HelloWorld object = skipList.ceiling(e);
						if(object .equals(e)) {
							object.metricMap.put(metricName, obj_rep);
						}
					}
					else {
						e.metricMap.put(metricName, obj_rep);
					}
					counter++;
				}
				minTimeStamp += 300000; 
			}
		}
		for(HelloWorld dataPoint : skipList) {
			System.out.println(dataPoint);
		}
	}

}
