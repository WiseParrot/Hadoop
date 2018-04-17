//package org.myorg;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

//import org.apache.hadoop.util.*;

public class MaxMeans {

	public static class Map extends MapReduceBase implements
			Mapper<LongWritable, Text, Text, Text> {

		private Text word = new Text();

		public void map(LongWritable key, Text value,
				OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {
			String line = value.toString();

			String NoOfStudents = " ";
			String CRmean = " ";
			String Mmean = " ";
			String Wmean = " ";
			String schoolName = " ";
			String val = " ";

			StringTokenizer tokenizer = new StringTokenizer(line, ",");
			while (tokenizer.hasMoreTokens() && tokenizer.countTokens() == 6) {
				tokenizer.nextToken();
				schoolName = tokenizer.nextToken();
				NoOfStudents = tokenizer.nextToken();
				CRmean = tokenizer.nextToken();
				Mmean = tokenizer.nextToken();
				Wmean = tokenizer.nextToken();	
				if (isNumeric(CRmean) && isNumeric(NoOfStudents)) {
					if (Integer.parseInt(NoOfStudents) > 5) {
						
						word.set("Test Takers");
						output.collect(word, new Text(NoOfStudents));
						
						val = schoolName + ',' + CRmean;
						word.set("CR Mean");
						output.collect(word, new Text(val));
						
						val = schoolName + ',' + Mmean;
						word.set("Math Mean");
						output.collect(word, new Text(val));
						
						val = schoolName + ',' + Wmean;
						word.set("Writing Mean");
						output.collect(word, new Text(val));
					}
				}
			}
		}
	}

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}

	public static class Reduce extends MapReduceBase implements
			Reducer<Text, Text, Text, IntWritable> {
		
		int totalcount = 0;
		
		public void reduce(Text key, Iterator<Text> values,
				OutputCollector<Text, IntWritable> output, Reporter reporter)
				throws IOException {
			
			String schoolNm = " ";
			String schoolName = " ";
			int Mean = 0;
			int MeanMax = 0;
			
			while (values.hasNext()) {
				if (values.equals(null)| values.equals(" ")){
					break;
				}
				StringTokenizer tkenizr = new StringTokenizer(values.next().toString(), ",");
				if (key.toString().contains("Test Takers")){
					totalcount += Integer.parseInt(tkenizr.nextToken());
					schoolName = "Total Number of Task Takers -->";
					MeanMax = totalcount;
				}
				if(tkenizr.countTokens() == 2){
					if (key.toString().contains("CR Mean")){
						schoolNm = "Highest CR Mean -->" + tkenizr.nextToken();
					}
					if (key.toString().contains("Math Mean")){
						schoolNm = "Highest Math Mean -->" + tkenizr.nextToken();
					}
					if (key.toString().contains("Writing Mean")){
						schoolNm = "Highest Writing Mean -->" + tkenizr.nextToken();
					}
					Mean = Integer.parseInt(tkenizr.nextToken());
				}
				if (MeanMax < Mean){
					MeanMax = Mean;
					schoolName = schoolNm;
				}
			}
			output.collect(new Text(schoolName), new IntWritable(MeanMax));
		}
	}

	public static void main(String[] args) throws Exception {
		JobConf conf = new JobConf(MaxMeans.class);
		conf.setJobName("MaxMeans");

		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);

		conf.setMapperClass(Map.class);
	//	 conf.setNumReduceTasks(0);
	//	conf.setCombinerClass(Reduce.class);
		conf.setReducerClass(Reduce.class);

		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);

		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));

		JobClient.runJob(conf);
	}
}
