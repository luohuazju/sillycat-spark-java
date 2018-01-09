package com.sillycat.sparkjava.app;

import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;

import com.sillycat.sparkjava.base.SparkBaseApp;

public class CountLinesOfKeywordApp extends SparkBaseApp {

	private static final long serialVersionUID = 7912776380212093020L;

	protected String getAppName() {
		return "CountLinesOfKeywordApp";
	}

	public void executeTask(List<String> params) {
		SparkConf conf = this.getSparkConf();
		SparkContext sc = new SparkContext(conf);

		String logFile = "file:////opt/spark/README.md";
		String keyword = "a";

		logger.info("Prepare the resource from " + logFile);
		JavaRDD<String> rdd = this.generateRdd(sc, logFile);
		logger.info("Executing the calculation based on keyword " + keyword);
		long result = processRows(rdd, keyword);
		logger.info("Lines with keyword " + keyword + ":" + result);
		sc.stop();
	}

	private JavaRDD<String> generateRdd(SparkContext sc, String logFile) {
		JavaRDD<String> logData = sc.textFile(logFile, 10).toJavaRDD();
		return logData;
	}

	private long processRows(JavaRDD<String> rows, String keyword) {
		JavaRDD<String> lines = rows.filter(new Function<String, Boolean>() {
			private static final long serialVersionUID = 1L;

			public Boolean call(String s) throws Exception {
				if (s == null || s.trim().length() < 1) {
					return false;
				}
				return true;
			}
		});
		long count = lines.count();
		return count;
	}
}
