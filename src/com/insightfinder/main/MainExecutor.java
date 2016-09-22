package com.insightfinder.main;

import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

import com.insightfinder.config.Config;
import com.insightfinder.core.CombinedMetricDataPoint;
import com.insightfinder.core.Metric;
import com.insightfinder.core.SingleMetricDataPoint;
import com.insightfinder.json.fetcher.JSONFetcher;
import com.insightfinder.json.parser.GenericJSONParser;
import com.insightfinder.output.CSVWriter;
import com.insightfinder.output.JSONWriter;
import com.insightfinder.output.XMLWriter;

public class MainExecutor {
	static final String URL_PART1 = "http://thho.net/files/insightfinder/techtest/";
	static final String URL_PART2 = ".json";
	static final String METRICS_URL = "http://thho.net/files/insightfinder/techtest/metrics.json";
	

	public static void main(String args[])throws Exception {
		
		String jsonString =  JSONFetcher.readJSONFromURL(METRICS_URL);
		GenericJSONParser<Metric> metricsJsonParser = new GenericJSONParser<Metric>();
		List <Metric> objectList = metricsJsonParser.parse(jsonString, "\n", Metric.class);
		ConcurrentSkipListSet<CombinedMetricDataPoint> skipList = new ConcurrentSkipListSet<CombinedMetricDataPoint>();
		for(Metric obj : objectList) {
			System.out.println(obj);
			String metricName = obj.getMetricName();
			jsonString =  JSONFetcher.readJSONFromURL(URL_PART1 +metricName + URL_PART2);
			GenericJSONParser<SingleMetricDataPoint> individualMetricJSONParser = new GenericJSONParser<SingleMetricDataPoint> ();
			List<SingleMetricDataPoint> list = individualMetricJSONParser.parse(jsonString, ",", SingleMetricDataPoint.class);
			int counter = 0;
			long minTimeStamp = list.get(0).getTime();
			while(counter < list.size()) {
				CombinedMetricDataPoint e = new CombinedMetricDataPoint();
				e.setTimestamp(minTimeStamp);
				if( minTimeStamp == list.get(counter).getTime()) {
					SingleMetricDataPoint obj_rep = list.get(counter);
					if(!skipList.add(e)) {
						CombinedMetricDataPoint object = skipList.ceiling(e);
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
		
		String outputOption = System.getProperty("insightfinder.metricsjson.output", Config.DEFAULT_OUTPUT_OPTION);
		
		if(outputOption.equals("csv")) {
			CSVWriter csvWriter = new CSVWriter();
			csvWriter.writeListToCSV(skipList);
			
		}
		else if (outputOption.equals("xml")) {
			XMLWriter xmlWriter = new XMLWriter();
			xmlWriter.convertListToXML(skipList);
		}
		else if (outputOption.equals("json")) {
			JSONWriter jsonWriter = new JSONWriter();
			jsonWriter.convertListToJSON(skipList); 
		}
		else {
			throw new Exception("Unsupported Output Format"); 
		}
	}

}
