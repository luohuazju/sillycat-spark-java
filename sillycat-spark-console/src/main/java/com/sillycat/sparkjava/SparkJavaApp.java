package com.sillycat.sparkjava;

import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.spark.SparkConf;
import org.apache.spark.deploy.yarn.Client;
import org.apache.spark.deploy.yarn.ClientArguments;

public class SparkJavaApp
{

  public static void main( String[] arguments ) throws Exception
  {

    String[] args = new String[]
    {
      "--class",
      "com.sillycat.sparkjava.SparkJavaApp",
      "--jar",
      "/home/ec2-user/users/carl/sillycat-spark-java/sillycat-spark-solr/target/sillycat-spark-solr-1.0-jar-with-dependencies.jar",
      "--arg",
      "com.sillycat.sparkjava.app.SeniorJavaFeedToXMLApp",
    };
    SparkConf sparkConf = new SparkConf();
    String applicationTag = "TestApp-" + new Date().getTime();
    sparkConf.setAppName( "SeniorJavaFeedToXMLApp" );
    //sparkConf.set( "spark.yarn.submit.waitAppCompletion", "false" );
    sparkConf.set( "spark.yarn.tags", applicationTag );
    sparkConf.set( "spark.master", "yarn-client" );
    sparkConf.set( "spark.submit.deployMode", "cluster" );
    //sparkConf.set( "spark.yarn.jars", "/opt/spark/jars/*.jar" );

    Configuration config = new Configuration();
    config.addResource( new Path( "/opt/hadoop/etc/hadoop/core-site.xml" ) );
    config.addResource( new Path( "/opt/hadoop/etc/hadoop/yarn-site.xml" ) );
    config.set( "fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName() );
    config.set( "fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName() );

    System.out.println( config.get( "yarn.resourcemanager.address" ) );

    System.setProperty( "SPARK_YARN_MODE", "true" );
    System.setProperty( "SPARK_HOME", "/opt/spark" );
    System.setProperty( "HADOOP_CONF_DIR", "/opt/hadoop/etc/hadoop" );

    ClientArguments cArgs = new ClientArguments( args );
    Client client = new Client( cArgs, config, sparkConf );
    client.run();

  }

}
