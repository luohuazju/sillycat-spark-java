#Run the local#

>java -jar target/sillycat-spark-solr-1.0-jar-with-dependencies.jar com.sillycat.sparkjava.app.CountLinesOfKeywordApp

>java -jar target/sillycat-spark-solr-1.0-jar-with-dependencies.jar com.sillycat.sparkjava.app.SeniorJavaFeedApp

>java -jar target/sillycat-spark-solr-1.0-jar-with-dependencies.jar com.sillycat.sparkjava.app.SeniorJavaFeedToXMLApp

#Run binary on local#

>bin/spark-submit --class com.sillycat.sparkjava.SparkJavaApp /Users/carl/work/sillycat/sillycat-spark-java/sillycat-spark-solr/target/sillycat-spark-solr-1.0-jar-with-dependencies.jar com.sillycat.sparkjava.app.CountLinesOfKeywordApp

>bin/spark-submit --class com.sillycat.sparkjava.SparkJavaApp /Users/carl/work/sillycat/sillycat-spark-java/sillycat-spark-solr/target/sillycat-spark-solr-1.0-jar-with-dependencies.jar com.sillycat.sparkjava.app.SeniorJavaFeedApp

#Run binary on Remote YARN Cluster#

>bin/spark-submit --class com.sillycat.sparkjava.SparkJavaApp --master yarn-client /home/ec2-user/users/carl/sillycat-spark-java/sillycat-spark-solr/target/sillycat-spark-solr-1.0-jar-with-dependencies.jar com.sillycat.sparkjava.app.CountLinesOfKeywordApp

>bin/spark-submit --class com.sillycat.sparkjava.SparkJavaApp --master yarn-client /home/ec2-user/users/carl/sillycat-spark-java/sillycat-spark-solr/target/sillycat-spark-solr-1.0-jar-with-dependencies.jar com.sillycat.sparkjava.app.SeniorJavaFeedApp