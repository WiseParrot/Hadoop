//package org.myorg;
	
import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
//import org.apache.hadoop.util.*;
	
public class TTCount {
	
public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {

	     private Text word = new Text();
	
	     public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
	       String line = value.toString();
	       
	       String temp = " ";
	       int numberofTT = 0;
	       
	       StringTokenizer tokenizer = new StringTokenizer(line,",");
	       while (tokenizer.hasMoreTokens() && tokenizer.countTokens() == 6) {
	    	   tokenizer.nextToken();
	    	   tokenizer.nextToken();
	    	   temp = tokenizer.nextToken();
	    	   
	    	   if(temp.matches("(.*)[a-z](.*)")|temp.matches("(.*)[A-Z](.*)")){
		    		   break;
	    	   }else{
	    		   numberofTT = Integer.parseInt(temp);
	    		   word.set("Total Count");
	    	   }
	       }
	        output.collect(word, new IntWritable(numberofTT));
	     }
	   }

	   public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
	     public void reduce(Text key, Iterator<IntWritable> values, OutputCollector< Text,IntWritable> output, Reporter reporter) throws IOException {
	    	 int totalTT = 0;
	       while (values.hasNext()) {
	    	   totalTT += values.next().get();
	       }
	       output.collect(key, new IntWritable(totalTT));
	     }
	   }
	
	   public static void main(String[] args) throws Exception {
	     JobConf conf = new JobConf(TTCount.class);
	     conf.setJobName("TTCount");
	
	     conf.setOutputKeyClass(Text.class);
	     conf.setOutputValueClass(IntWritable.class);
	
	     conf.setMapperClass(Map.class);
//	     conf.setNumReduceTasks(0);
//	     conf.setCombinerClass(Reduce.class);
	     conf.setReducerClass(Reduce.class);
	
	     conf.setInputFormat(TextInputFormat.class);
	     conf.setOutputFormat(TextOutputFormat.class);
	
	     FileInputFormat.setInputPaths(conf, new Path(args[0]));
	     FileOutputFormat.setOutputPath(conf, new Path(args[1]));
	
	     JobClient.runJob(conf);
	   }
	}
	