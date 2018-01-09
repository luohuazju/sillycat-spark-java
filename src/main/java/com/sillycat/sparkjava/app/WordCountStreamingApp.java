package com.sillycat.sparkjava.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import com.sillycat.sparkjava.base.SparkBaseApp;

public class WordCountStreamingApp extends SparkBaseApp {

	private static final long serialVersionUID = 7401544141510431796L;

	protected String getAppName() {
		return "WordCountStreamingApp";
	}

	public void executeTask(List<String> params) {
		SparkConf conf = this.getSparkConf();
		// The time interval at which streaming data will be divided into
		// batches
		JavaStreamingContext ssc = new JavaStreamingContext(conf, new Duration(30000));
		ssc.checkpoint(this.getAppName());

		logger.info("Prepare the resource for streaming");
		generateRdd(ssc, "carl");

		try {
			ssc.start();
			ssc.awaitTermination();
		} catch (InterruptedException e) {
			logger.error("InterruptedException:", e);
		}
	}

	private JavaRDD<String> generateRdd(JavaStreamingContext ssc, String keyword) {
		String zkQuorum = "localhost:2181";
		String group = "1";
		String topics = "top1,top2";
		int numThreads = 2;
		Map<String, Integer> topicmap = new HashMap<>();
		String[] topicsArr = topics.split(",");
		int n = topicsArr.length;
		for (int i = 0; i < n; i++) {
			topicmap.put(topicsArr[i], numThreads);
		}
		JavaPairReceiverInputDStream<String, String> lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicmap);
		return null;
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
