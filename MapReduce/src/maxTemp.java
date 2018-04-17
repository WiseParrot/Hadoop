//package org.myorg;
	
import java.io.IOException;
import java.util.*;
	
import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
//import org.apache.hadoop.util.*;
	
public class maxTemp {
	
public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, IntWritable, IntWritable> {
	     private final static IntWritable year = new IntWritable ();
	     private final static IntWritable temp = new IntWritable ();
//	     private Text word = new Text();
//	     private int size = 0;
//	     private IntWritable [] yrTmp = new IntWritable();
	
	     public void map(LongWritable key, Text value, OutputCollector<IntWritable, IntWritable> output, Reporter reporter) throws IOException {
	       String line = value.toString();
	       StringTokenizer tokenizer = new StringTokenizer(line);
//	       for (new int val : tokenizer){
//	    	   
//	       }
	       while (tokenizer.hasMoreTokens()) {
	        // word.set
	         year.set(Integer.parseInt(tokenizer.nextToken()));
	         temp.set(Integer.parseInt(tokenizer.nextToken()));
	         //word = size;
	      //   System.out.println(year + "   " + temp);
	         output.collect(year, temp);
	       }
	     }
	   }
	
	   public static class Reduce extends MapReduceBase implements Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {
	     public void reduce(IntWritable key, Iterator<IntWritable> values, OutputCollector<IntWritable, IntWritable> output, Reporter reporter) throws IOException {
	    	 int maxtmp = Integer.MIN_VALUE;
	       while (values.hasNext()) {
	    	   int temp = values.next().get();
	    	   if (temp > maxtmp){
	    		   maxtmp = temp;
	    	   }
	       }
	       output.collect(key, new IntWritable(maxtmp));
	     }
	   }
	
	   public static void main(String[] args) throws Exception {
	     JobConf conf = new JobConf(maxTemp.class);
	     conf.setJobName("maxTemp");
	
	     conf.setOutputKeyClass(IntWritable.class);
	     conf.setOutputValueClass(IntWritable.class);
	
	     conf.setMapperClass(Map.class);
	     conf.setCombinerClass(Reduce.class);
	     conf.setReducerClass(Reduce.class);
	
	     conf.setInputFormat(TextInputFormat.class);
	     conf.setOutputFormat(TextOutputFormat.class);
	
	     FileInputFormat.setInputPaths(conf, new Path(args[0]));
	     FileOutputFormat.setOutputPath(conf, new Path(args[1]));
	
	     JobClient.runJob(conf);
	   }
	}
	