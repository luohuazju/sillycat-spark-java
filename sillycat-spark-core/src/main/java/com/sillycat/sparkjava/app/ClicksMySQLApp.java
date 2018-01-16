package com.sillycat.sparkjava.app;

import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;

import com.sillycat.sparkjava.base.SparkBaseApp;

public class ClicksMySQLApp extends SparkBaseApp {

	private static final long serialVersionUID = -277197988584555417L;
	
	protected String getAppName() {
		return "ClicksMySQLApp";
	}
	
	public void executeTask(List<String> params) {
		SparkConf conf = this.getSparkConf();
		SparkContext sc = new SparkContext(conf);

		
//		String logFile = "file:////opt/spark/README.md";
//		String keyword = "a";
//
//		logger.info("Prepare the resource from " + logFile);
//		JavaRDD<String> rdd = this.generateRdd(sc, logFile);
//		logger.info("Executing the calculation based on keyword " + keyword);
//		long result = processRows(rdd, keyword);
//		logger.info("Lines with keyword " + keyword + ":" + result);
//		sc.stop();
	}

}
