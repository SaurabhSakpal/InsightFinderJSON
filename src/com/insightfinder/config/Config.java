package com.insightfinder.config;

public class Config {
	
	/*
	 * This parameter determines the output of the program
	 * 
	 * Currently we have enabled "csv" as default output option
	 * 
	 * Other DEFAULT_OUTPUT_OPTION are "xml" and "json"
	 */
	
	public static final String DEFAULT_OUTPUT_OPTION = "csv";
	
	/*
	 * Following three are the input locations for the .csv , .xml and .json output files
	 * Make sure the locations are changed before running the program  
	 */
	public static final String OUTPUT_CSV_FILE_LOCATION = "/home/saurabh/workspace_2/InsightFinder/resources/"; 
	public static final String OUTPUT_XML_FILE_LOCATION = "/home/saurabh/workspace_2/InsightFinder/resources/"; 
	public static final String OUTPUT_JSON_FILE_LOCATION = "/home/saurabh/workspace_2/InsightFinder/resources/";
	
	/*
	 * This is the time difference between two consecutive data points
	 * So you start from lowest possible timestamp and go on increasing by following defined value  
	 */
	public static final Long TIMESTAMP_DT = 300000L;

}
