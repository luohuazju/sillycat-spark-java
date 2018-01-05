package com.sillycat.sparkjava.base;

import java.io.Serializable;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;

public class SparkBaseApp implements Serializable {

	private static final long serialVersionUID = 3926361971198654215L;

	public void executeTask(List<String> params) {

	}

	protected String getAppName() {
		return "defaultJob";
	}

	protected SparkConf getSparkConf() {
		SparkConf conf = new SparkConf();
		conf.setAppName(this.getAppName());
		conf.setIfMissing("spark.master", "local[4]");
		conf.setSparkHome("/opt/spark");
		conf.setJars(SparkContext.jarOfClass(this.getClass()).toList());
		conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
		return conf;
	}

}
