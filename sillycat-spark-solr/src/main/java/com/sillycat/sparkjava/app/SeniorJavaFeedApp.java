//package com.sillycat.sparkjava.app;
//
//import java.util.List;
//
//import org.apache.solr.common.SolrDocument;
//import org.apache.spark.SparkConf;
//import org.apache.spark.SparkContext;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.function.Function;
//
//import com.lucidworks.spark.rdd.SolrJavaRDD;
//import com.sillycat.sparkjava.base.SparkBaseApp;
//
//public class SeniorJavaFeedApp extends SparkBaseApp {
//
//	private static final long serialVersionUID = -1219898501920199612L;
//
//	protected String getAppName() {
//		return "SeniorJavaFeedApp";
//	}
//
//	public void executeTask(List<String> params) {
//		SparkConf conf = this.getSparkConf();
//		SparkContext sc = new SparkContext(conf);
//
//		String zkHost = "zookeeper1.us-east-1.elasticbeanstalk.com,zookeeper2.us-east-1.elasticbeanstalk.com,zookeeper3.us-east-1.elasticbeanstalk.com/solr/allJobs";
//		String collection = "allJobs";
//		String solrQuery = "expired: false AND title: Java* AND source_id: 4675";
//		String keyword = "Architect";
//
//		logger.info("Prepare the resource from " + solrQuery);
//		JavaRDD<SolrDocument> rdd = this.generateRdd(sc, zkHost, collection, solrQuery);
//		logger.info("Executing the calculation based on keyword " + keyword);
//
//		List<SolrDocument> results = processRows(rdd, keyword);
//		for (SolrDocument result : results) {
//			logger.info("Find some jobs for you:" + result);
//		}
//		sc.stop();
//	}
//
//	private JavaRDD<SolrDocument> generateRdd(SparkContext sc, String zkHost, String collection, String solrQuery) {
//		SolrJavaRDD solrRDD = SolrJavaRDD.get(zkHost, collection, sc);
//		JavaRDD<SolrDocument> resultsRDD = solrRDD.queryShards(solrQuery);
//		return resultsRDD;
//	}
//
//	private List<SolrDocument> processRows(JavaRDD<SolrDocument> rows, String keyword) {
//		JavaRDD<SolrDocument> lines = rows.filter(new Function<SolrDocument, Boolean>() {
//			private static final long serialVersionUID = 1L;
//
//			public Boolean call(SolrDocument s) throws Exception {
//				Object titleObj = s.getFieldValue("title");
//				if (titleObj != null) {
//					String title = titleObj.toString();
//					if (title.contains(keyword)) {
//						return true;
//					}
//				}
//				return false;
//			}
//		});
//		return lines.collect();
//	}
//
//}
