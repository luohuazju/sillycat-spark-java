package com.sillycat.sparkjava.app;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

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
		logger.info("Start to have the streaming");
		JavaStreamingContext ssc = new JavaStreamingContext(conf, new Duration(30000));
		ssc.checkpoint(this.getAppName());
		logger.info("Prepare the resource for streaming");
		processStream(ssc, "carl");
		logger.info("Streaming is working");
		try {
			ssc.start();
			ssc.awaitTermination();
		} catch (InterruptedException e) {
			logger.error("InterruptedException:", e);
		}
	}

	private void processStream(JavaStreamingContext ssc, String keyword) {

		Map<String, Object> kafkaParams = new HashMap<>();
		kafkaParams.put("bootstrap.servers", "fr-stage-api:9092,fr-stage-consumer:9092,fr-perf1:9092");
		kafkaParams.put("key.deserializer", StringDeserializer.class);
		kafkaParams.put("value.deserializer", StringDeserializer.class);
		kafkaParams.put("group.id", "WordCountStreamingApp");
		kafkaParams.put("auto.offset.reset", "latest");
		kafkaParams.put("enable.auto.commit", true);
		Collection<String> topics = Arrays.asList("sillycat-topic");

		logger.info("Init the Kafka Clients to fetch lines");
		JavaInputDStream<ConsumerRecord<String, String>> dStream = KafkaUtils.createDirectStream(ssc,
				LocationStrategies.PreferConsistent(),
				ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams));
		dStream.foreachRDD(rdd -> {
			processRows(rdd, keyword);
		});
	}

	private void processRows(JavaRDD<ConsumerRecord<String, String>> rdds, String keyword) {
		JavaRDD<String> rows = rdds.map(record -> record.value());
		JavaRDD<String> lines = rows.filter(new Function<String, Boolean>() {
			private static final long serialVersionUID = 1L;

			public Boolean call(String s) throws Exception {
				if (s == null || s.trim().length() < 1) {
					return false;
				}
				if (!s.contains(keyword)) {
					return false;
				}
				return true;
			}
		});
		long count = lines.count();
		logger.info("Kafka received " + count + " " + keyword);
	}

}
