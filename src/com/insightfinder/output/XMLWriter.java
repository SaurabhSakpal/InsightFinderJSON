package com.insightfinder.output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentSkipListSet;

import com.insightfinder.config.Config;
import com.insightfinder.core.CombinedMetricDataPoint;

public class XMLWriter {
	
	public void convertListToXML(ConcurrentSkipListSet<CombinedMetricDataPoint> skipList) throws IOException {
		FileWriter writer = new FileWriter(new File(Config.OUTPUT_XML_FILE_LOCATION+"test.xml"), false);
		String metrics[] = {"CPUUtilization" ,"MemoryUsage" , "DiskReadBytes", "DiskWriteBytes", "NetworkIn", "NetworkOut"};
		String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n";
		StringBuilder sb = new StringBuilder();
		
		sb.append(header);
		sb.append("<Metrics>\n");
		
		for (CombinedMetricDataPoint combinedDataPoint : skipList) {
			sb.append("    <Timestamp value=\"" +combinedDataPoint.getTimestamp()+"\">\n");
			for(String metric : metrics) {
				sb.append("        <" + metric + ">");
				String metricAverage = "";
				if(combinedDataPoint.metricMap.containsKey(metric)) {
					metricAverage = combinedDataPoint.metricMap.get(metric).getAverage();
				}
				sb.append(metricAverage);
				sb.append("</" + metric + ">\n");
			}
			sb.append("    </Timestamp>");
			sb.append('\n');
		}
		sb.append("</Metrics>");
		System.out.println(sb);
		writer.write(sb.toString());
        writer.close();
	}

}
