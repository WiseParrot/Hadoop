//package org.myorg;
	
import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
//import org.apache.hadoop.util.*;
	
public class SATMR {
	
public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, IntWritable, Text> {

	     //private final static IntWritable one = new IntWritable(1);
	     private Text word = new Text();
	
	     public void map(LongWritable key, Text value, OutputCollector<IntWritable, Text> output, Reporter reporter) throws IOException {
	       String line = value.toString();
	       
	       String schoolname = " ";
	       String temp = " ";
	       int numberoftakers = 0;
	       
	       StringTokenizer tokenizer = new StringTokenizer(line,",");
	       while (tokenizer.hasMoreTokens() && tokenizer.countTokens() == 6) {
	    	   tokenizer.nextToken();
	    	   schoolname = tokenizer.nextToken();
	    	   temp = tokenizer.nextToken();
	    	   
	    	   if(temp.matches("(.*)[a-z](.*)")|temp.matches("(.*)[A-Z](.*)")){
		    		   break;
	    	   }else{
	    		   numberoftakers = Integer.parseInt(temp);
	    		   if (numberoftakers<6){
	    			   break;
	    		   }
	    	   }
	    	   int cat = categoryTakers(numberoftakers);
	         word.set(schoolname);
	        output.collect(new IntWritable(cat), word);
	       }
	     }
	   }

	public static int categoryTakers (int takers){
	
		return takers/100;
		
	}
	
	   public static class Reduce extends MapReduceBase implements Reducer<IntWritable, Text, IntWritable, Text> {
	     public void reduce(IntWritable key, Iterator<Text> values, OutputCollector<IntWritable, Text> output, Reporter reporter) throws IOException {
	       String schoolnames = " ";
	       while (values.hasNext()) {
	    	   schoolnames += values.next() + " / ";
	       }
	       output.collect(key, new Text(schoolnames));
	     }
	   }
	
	   public static void main(String[] args) throws Exception {
	     JobConf conf = new JobConf(SATMR.class);
	     conf.setJobName("SATMR");
	
	     conf.setOutputKeyClass(IntWritable.class);
	     conf.setOutputValueClass(Text.class);
	
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
	