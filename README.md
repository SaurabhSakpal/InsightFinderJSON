# README
## Run Instructions
- Unzip the InsightFinderJSON.zip or clone the git repository
- Open the project in eclipse
- Update the location of the csv/xml/json output file in the Config.java 
- Run the MainExecutor class

## External Libraries
No external Libraries are used in the code

## Structure of the Project
- com.insightfinder.config.Config.java holds all the project related configurations. Please update the location of the output file before running the project
- com.insightfinder.main.MainExecutor.java contians the main method
- com.insightfinder.json.fetcher.JSONFetcher contains the logic to read all the input JSONs from urls
- package com.insightfinder.core has all the necessary POJOs
- com.insightfinder.json.parser.GenericParser is the JSON parser that I worte for this project

