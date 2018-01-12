#Run the local#

>java -jar target/sillycat-spark-java-1.0-jar-with-dependencies.jar com.sillycat.sparkjava.app.CountLinesOfKeywordApp

>java -jar target/sillycat-spark-java-1.0-jar-with-dependencies.jar com.sillycat.sparkjava.app.WordCountStreamingApp

#Run binary on local#

>bin/spark-submit --class com.sillycat.sparkjava.SparkJavaApp /Users/carl/work/sillycat/sillycat-spark-java/target/sillycat-spark-java-1.0-jar-with-dependencies.jar com.sillycat.sparkjava.app.CountLinesOfKeywordApp

#Run binary on Remote YARN Cluster#

>bin/spark-submit --class com.sillycat.sparkjava.SparkJavaApp --master yarn-client /home/ec2-user/users/carl/sillycat-spark-java/target/sillycat-spark-java-1.0-jar-with-dependencies.jar com.sillycat.sparkjava.app.CountLinesOfKeywordApp