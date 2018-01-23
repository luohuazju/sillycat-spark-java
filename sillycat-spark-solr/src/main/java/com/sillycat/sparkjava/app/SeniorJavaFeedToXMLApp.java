package com.sillycat.sparkjava.app;

import java.math.BigDecimal;
import java.util.List;

import org.apache.solr.common.SolrDocument;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import com.lucidworks.spark.rdd.SolrJavaRDD;
import com.sillycat.sparkjava.base.SparkBaseApp;

public class SeniorJavaFeedToXMLApp extends SparkBaseApp
{

  private static final long serialVersionUID = 8364133452168714109L;

  @Override
  protected String getAppName()
  {
    return "SeniorJavaFeedToXMLApp";
  }

  @Override
  public void executeTask( List<String> params )
  {
    SparkConf conf = this.getSparkConf();
    SparkContext sc = new SparkContext( conf );
    SparkSession sqlSession = new SparkSession( sc );

    String zkHost =
      "zookeeper1.us-east-1.elasticbeanstalk.com,zookeeper2.us-east-1.elasticbeanstalk.com,zookeeper3.us-east-1.elasticbeanstalk.com/solr/allJobs";
    String collection = "allJobs";
    //String solrQuery = "expired: false AND title: Java* AND source_id: 4675";
    String solrQuery = "expired: false AND title: Java*";
    String keyword = "Developer";

    logger.info( "Prepare the resource from " + solrQuery );
    JavaRDD<SolrDocument> rdd = this.generateRdd( sc, zkHost, collection, solrQuery );
    logger.info( "System get sources job count:" + rdd.count() );

    logger.info( "Executing the calculation based on keyword " + keyword );
    JavaRDD<SolrDocument> solrDocs = processRowFilters( rdd, keyword );
    JavaRDD<Job> jobs = solrDocs.map( new Function<SolrDocument, Job>()
    {

      private static final long serialVersionUID = -4456732708499340880L;

      @Override
      public Job call( SolrDocument solr ) throws Exception
      {
        Job job = new Job();
        job.setId( solr.getFieldValue( "id" ).toString() );
        job.setTitle( solr.getFieldValue( "title" ).toString() );
        job.setPrice( new BigDecimal( solr.getFieldValue( "cpc" ).toString() ) );
        return job;
      }

    } );

    Dataset<Row> jobDF = sqlSession.createDataFrame( jobs, Job.class );
    jobDF.createOrReplaceTempView( "job" );
    Dataset<Row> jobHighDF = sqlSession.sql( "SELECT id, title, price FROM job WHERE price > 16 " );

    logger.info( "Find some jobs for you:" + jobHighDF.count() );
    logger.info( "Job Content is:" + jobHighDF.collectAsList().get( 0 ) );

    List<Job> jobsHigh = jobHighDF.as( Encoders.bean( Job.class ) ).collectAsList();

    logger.info( "Persist some jobs to XML:" + jobsHigh.size() );
    logger.info( "Persist some jobs to XML:" + jobsHigh.get( 0 ) );

    XMLUtil.xmlStreamWriter( "jobs.xml", jobsHigh );

    sqlSession.close();
    sc.stop();
  }

  private JavaRDD<SolrDocument> generateRdd( SparkContext sc, String zkHost, String collection, String solrQuery )
  {
    SolrJavaRDD solrRDD = SolrJavaRDD.get( zkHost, collection, sc );
    JavaRDD<SolrDocument> resultsRDD = solrRDD.queryShards( solrQuery );
    return resultsRDD;
  }

  private JavaRDD<SolrDocument> processRowFilters( JavaRDD<SolrDocument> rows, String keyword )
  {
    JavaRDD<SolrDocument> lines = rows.filter( new Function<SolrDocument, Boolean>()
    {
      private static final long serialVersionUID = 1L;

      @Override
      public Boolean call( SolrDocument s ) throws Exception
      {
        Object titleObj = s.getFieldValue( "title" );
        if ( titleObj != null )
        {
          String title = titleObj.toString();
          if ( title.toLowerCase().contains( keyword.toLowerCase() ) )
          {
            return true;
          }
        }
        return false;
      }
    } );
    return lines;
  }

}
