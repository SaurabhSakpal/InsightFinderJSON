package com.insightfinder.main;

import java.io.IOException;
import java.net.MalformedURLException;
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
		
		ConcurrentSkipListSet<CombinedMetricDataPoint> skipList = readInput();
		
		generateOutput(skipList);
	}


	private static ConcurrentSkipListSet<CombinedMetricDataPoint> readInput() throws IOException, MalformedURLException {
		
		ConcurrentSkipListSet<CombinedMetricDataPoint> skipList = new ConcurrentSkipListSet<CombinedMetricDataPoint>();
		String jsonString =  JSONFetcher.readJSONFromURL(METRICS_URL);
		GenericJSONParser<Metric> metricsJsonParser = new GenericJSONParser<Metric>();
		List <Metric> objectList = metricsJsonParser.parse(jsonString, "\n", Metric.class);
		
		for (Metric metric : objectList) {
			String metricName = metric.getMetricName();
			jsonString =  JSONFetcher.readJSONFromURL(URL_PART1 + metricName + URL_PART2);
			
			GenericJSONParser<SingleMetricDataPoint> individualMetricJSONParser = new GenericJSONParser<SingleMetricDataPoint> ();
			List<SingleMetricDataPoint> list = individualMetricJSONParser.parse(jsonString, ",", SingleMetricDataPoint.class);
			
			int counter = 0;
			long currentTimeStamp = list.get(0).getTime();
			
			while (counter < list.size()) {
				CombinedMetricDataPoint combinedDataPoint = new CombinedMetricDataPoint();
				combinedDataPoint.setTimestamp(currentTimeStamp);
				
				if (currentTimeStamp == list.get(counter).getTime()) {
					SingleMetricDataPoint metricDataPoint = list.get(counter);
					
					if (!skipList.add(combinedDataPoint)) {
						CombinedMetricDataPoint storedDataPoint = skipList.ceiling(combinedDataPoint);
						
						if (storedDataPoint.equals(combinedDataPoint)) {
							storedDataPoint.metricMap.put(metricName, metricDataPoint);
						}
					}
					else {
						combinedDataPoint.metricMap.put(metricName, metricDataPoint);
					}
					counter++;
				}
				currentTimeStamp += Config.TIMESTAMP_DT; 
			}
		}
		return skipList;
	}


	private static void generateOutput(ConcurrentSkipListSet<CombinedMetricDataPoint> skipList) throws Exception {
		
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
