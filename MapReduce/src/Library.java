 //package org.myorg;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;


public class Library {

	public static String key1 = "";
	public static final Text value1 = new Text("1");

	public static class Map extends MapReduceBase implements
			Mapper<Text, Text, Text, Text> {

		String Name = "";
		String Ph = "";
		String Mon = "";
		String Tue = "";
		String Wed = "";
		String Thu = "";
		String Fri = "";
		String Sat = "";
		String Sun = "";
		int sundayClosedCnt = 0;

		public void map(Text key, Text value,
				OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {

			key1 = key.toString();
			
			/*
			 * My Objective is to divide all the records in three types of key, value pairs.
			 * They will be counted in the Reducer per key. The three types of <key, value> pairs were:
			 * <1. Not Working Anymore -->, 1>
			 * <2. Sunday Open -->, 1>
			 * <3. Total Count -->, 1>
			 */
			if ((key.getLength() > 50) && (!key1.contains("name,phone"))) {

				if (!key1.isEmpty()) {
					StringTokenizer tokenizer = new StringTokenizer(key1, ",");

					key1 = "3. Total Count -->";
					
					if (tokenizer.countTokens() >= 9) {
						Name = tokenizer.nextToken();
						Ph = tokenizer.nextToken();
						Mon = tokenizer.nextToken();
						Tue = tokenizer.nextToken();
						Wed = tokenizer.nextToken();
						Thu = tokenizer.nextToken();
						Fri = tokenizer.nextToken();
						Sat = tokenizer.nextToken();
						Sun = tokenizer.nextToken();
						
						
						if (!Sun.contains("closed")) {
							key1 = "2. Sunday Open -->";
						}
						if (Mon.contains("closed") && Tue.contains("closed")
								&& Wed.contains("closed")
								&& Thu.contains("closed")
								&& Fri.contains("closed")
								&& Sat.contains("closed")
								&& Sun.contains("closed")) {
							key1 = "1. Not Working Anymore -->";
						}
					}
				}
				output.collect(new Text(key1), value1);
			}

		}
	}

	public static class Reduce extends MapReduceBase implements
			Reducer<Text, Text, Text, Text> {

		 int totalcount = 0;
		 int totalcount1 = 0;
		 int totalcount2 = 0;
		 int totalcount3 = 0;

		public void reduce(Text key, Iterator<Text> values,
				OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {

			String count = "";
			
			if (key.toString().contains("1. Not Working Anymore")) {
				while (values.hasNext()) {
					totalcount1++;
					values.next();
				}
				count = Integer.toString(totalcount1);
				output.collect(new Text(key), new Text(count));
			}
			if (key.toString().contains("2. Sunday Open")) {
				while (values.hasNext()) {
					totalcount2++;
					values.next();
				}
				count = Integer.toString(totalcount2);
				output.collect(new Text(key), new Text(count));
			}
			if (key.toString().contains("3. Total Count")|key.equals(" ")) {
				while (values.hasNext()) {
					totalcount3++;
					values.next();
				}
				totalcount = totalcount1 + totalcount2 + totalcount3;
				count = Integer.toString(totalcount);
				output.collect(new Text(key), new Text(count));
			}

		}
	}

	public static void main(String[] args) throws Exception {
		JobConf conf = new JobConf(Library.class);
		conf.setJobName("Library");

		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);

		conf.setMapperClass(Map.class);
		// conf.setNumReduceTasks(0);
		conf.setReducerClass(Reduce.class);
		// conf.setCombinerClass(Reduce.class);

		conf.setInputFormat(KeyValueTextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);

		conf.set("key.value.separator.in.input.line", "\"");

		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));

		JobClient.runJob(conf);
	}
}
