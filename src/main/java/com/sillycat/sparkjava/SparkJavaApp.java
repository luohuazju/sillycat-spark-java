package com.sillycat.sparkjava;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sillycat.sparkjava.base.SparkBaseApp;

public class SparkJavaApp {

	protected final static Logger logger = LoggerFactory.getLogger(SparkJavaApp.class);

	public static void main(String[] args) {
		logger.info("Start to Spark tasks------");
		String className = "com.sillycat.sparkjava.app.CountLinesOfKeywordApp";
		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				logger.info("args: " + args[i]);
			}
			logger.info("first args :" + args[0]);
			className = args[0];
		}
		logger.info("className: " + className);
		logger.info("--------------------------");
		Class<?> classInstance;
		try {
			classInstance = Class.forName(className);
			SparkBaseApp task = (SparkBaseApp) classInstance.newInstance();
			task.executeTask(Arrays.asList(args));
		} catch (ClassNotFoundException e) {
			logger.info("ClassNotFoundException:", e);
		} catch (InstantiationException e) {
			logger.info("InstantiationException:", e);
		} catch (IllegalAccessException e) {
			logger.info("IllegalAccessException:", e);
		}

	}

}
