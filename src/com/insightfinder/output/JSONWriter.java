package com.insightfinder.output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentSkipListSet;

import com.insightfinder.config.Config;
import com.insightfinder.core.CombinedMetricDataPoint;

public class JSONWriter {
	
	public void convertListToJSON (ConcurrentSkipListSet<CombinedMetricDataPoint> skipList) throws IOException {
		FileWriter writer = new FileWriter(new File(Config.OUTPUT_JSON_FILE_LOCATION+"test.json"), false);
		String metrics[] = {"CPUUtilization" ,"MemoryUsage" , "DiskReadBytes", "DiskWriteBytes", "NetworkIn", "NetworkOut"};
		String header = "{\n";
		StringBuilder sb = new StringBuilder();
		
		sb.append(header);
		sb.append("    \"Metrics\": [\n");
		
		for (CombinedMetricDataPoint combinedDataPoint : skipList) {
			sb.append("        {\n");
			sb.append("            \"Timestamp\" : \"" +combinedDataPoint.getTimestamp()+"\",\n");
			for(String metric : metrics) {
				if(combinedDataPoint.metricMap.containsKey(metric)) {
					sb.append("            \"" + metric + "\" : \"");
					String metricAverage = combinedDataPoint.metricMap.get(metric).getAverage();
					sb.append(metricAverage);
					sb.append("\",\n");
				}
			}
			sb.deleteCharAt(sb.length()-2);
			sb.append("        },\n");
		}
		sb.deleteCharAt(sb.length()-2);
		sb.append("    ]\n");
		sb.append("}");
		System.out.println(sb);
		writer.write(sb.toString());
        writer.close();
	}

}
