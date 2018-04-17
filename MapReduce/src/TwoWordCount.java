//package org.myorg;
import java.io.IOException;
import java.util.*;
import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
//import org.apache.hadoop.util.*;



public class TwoWordCount {
	
	public static class mymap extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable>{
		private Text word = new Text();
		//private final static IntWritable count = new IntWritable(1);
		public void map(LongWritable key, Text value, OutputCollector <Text , IntWritable> output, Reporter reporter) throws IOException{
			String line = value.toString();
			String ww1ord="", ww2ord="";
					//wword = "";
			//int i = 1;
			StringTokenizer mytoken  = new StringTokenizer(line);
			
			ww2ord = ww1ord = mytoken.nextToken();
		//	ww2ord = mytoken.nextToken();
			while(mytoken.hasMoreTokens()){
				if (mytoken.hasMoreTokens()){
					ww1ord = ww2ord;
					ww2ord = mytoken.nextToken();
				}
					//wword = ww1ord + " " + ww2ord;
					word.set(ww1ord + " " + ww2ord);
					output.collect(word, new IntWritable(1));
			}
		}
		
	}
	
	public static class myreducer extends MapReduceBase implements Reducer <Text, IntWritable, Text, IntWritable>{
		public void reduce(Text key, Iterator <IntWritable> values, OutputCollector <Text, IntWritable> output, Reporter reporter) throws IOException{
			int sumcount = 0;
			while(values.hasNext()){
				sumcount += values.next().get();
			}
			output.collect(key, new IntWritable(sumcount));
		}
	}

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		JobConf myjob = new JobConf(TwoWordCount.class);
		myjob.setJobName("TwoWordCount");
		
		myjob.setOutputKeyClass(Text.class);
		myjob.setOutputValueClass(IntWritable.class);
		
		myjob.setMapperClass(mymap.class);
		myjob.setCombinerClass(myreducer.class);
		myjob.setReducerClass(myreducer.class);
		
		myjob.setInputFormat(TextInputFormat.class);
		myjob.setOutputFormat(TextOutputFormat.class);
		
		
		FileInputFormat.setInputPaths(myjob, new Path(args[0]));
	    FileOutputFormat.setOutputPath(myjob, new Path(args[1]));
		JobClient.runJob(myjob);
		
		
		
		
	}

}
