package com.sillycat.sparkjava.base;

import java.io.Serializable;
import java.util.List;

public class SparkBaseApp implements Serializable {

	private static final long serialVersionUID = 3926361971198654215L;

	protected void executeTask(List<String> params) {

	}
	
	protected String getAppName() {
		return "defaultJob";
	}
	
	protected SparkConf getSparkConf(Config config) {
		
	}

}
