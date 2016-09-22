package com.insightfinder.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentSkipListSet;

import com.insightfinder.config.Config;
import com.insightfinder.core.CombinedMetricDataPoint;

public class CSVWriter {
	
	public void writeListToCSV(ConcurrentSkipListSet<CombinedMetricDataPoint> skipList) throws IOException {
			FileWriter writer = new FileWriter(new File(Config.OUTPUT_CSV_FILE_LOCATION+"test.csv"), false);
			String metrics[] = {"CPUUtilization" ,"MemoryUsage" , "DiskReadBytes", "DiskWriteBytes", "NetworkIn", "NetworkOut"};
			StringBuilder firstLine = new StringBuilder();
			firstLine.append("Timestamp");
			for(String metric : metrics) {
				firstLine.append(',');
				firstLine.append(metric);
			}
			firstLine.append('\n');
			StringBuilder sb = new StringBuilder();
			sb.append(firstLine);
			for (CombinedMetricDataPoint combinedDataPoint : skipList) {
				sb.append(combinedDataPoint.getTimestamp());
				for(String metric : metrics) {
					String metricAverage = "";
					if(combinedDataPoint.metricMap.containsKey(metric)) {
						metricAverage = combinedDataPoint.metricMap.get(metric).getAverage();
					}
					sb.append(',');
					sb.append(metricAverage);
				}
				sb.append('\n');
				
			}
			System.out.println(sb);
	        writer.write(sb.toString());
	        writer.close();
	        System.out.println("done!");		
	}

}
