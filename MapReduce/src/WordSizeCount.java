//package org.myorg;
	
import java.io.IOException;
import java.util.*;
	
import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
//import org.apache.hadoop.util.*;
	
public class WordSizeCount {
	
public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, IntWritable, IntWritable> {
	     private final static IntWritable one = new IntWritable(1);
	     private final static IntWritable wordsize = new IntWritable();
//	     private Text word = new Text();
//	     private int size = 0;
	
	     public void map(LongWritable key, Text value, OutputCollector<IntWritable, IntWritable> output, Reporter reporter) throws IOException {
	       String line = value.toString();
	       StringTokenizer tokenizer = new StringTokenizer(line);
	       while (tokenizer.hasMoreTokens()) {
	        // word.set
	         wordsize.set(tokenizer.nextToken().length());
	         //word = size;
	         output.collect(wordsize, one);
	       }
	     }
	   }
	
	   public static class Reduce extends MapReduceBase implements Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {
	     public void reduce(IntWritable key, Iterator<IntWritable> values, OutputCollector<IntWritable, IntWritable> output, Reporter reporter) throws IOException {
	       int sum = 0;
	       while (values.hasNext()) {
	         sum += values.next().get();
	       }
	       output.collect(key, new IntWritable(sum));
	     }
	   }
	
	   public static void main(String[] args) throws Exception {
	     JobConf conf = new JobConf(WordSizeCount.class);
	     conf.setJobName("wordsizecount");
	
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
	