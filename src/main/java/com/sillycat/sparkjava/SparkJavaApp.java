package com.sillycat.sparkjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SparkJavaApp {

	protected final static Logger logger = LoggerFactory.getLogger(SparkJavaApp.class);

	public static void main(String[] args) {
		logger.info("enter the methods------");
		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				logger.info("args:" + args[i]);
			}
		}
		logger.info("-----------------------");
	}

}
