//package org.myorg;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;


public class LibraryFull {

	public static String key1 = "";
	public static String value1 = "";

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
			/*
			 * This piece of code wasn't necessary for the problem statement
			 * given. But, I tried to learn how to parse file with '\n'
			 * characters in file. The goal is to show the FULL address of the
			 * School along with names for any given criteria.
			 */
			if (key.getLength() > 50) {
				key1 = key.toString();
				value1 = value.toString();
			} else if (key.getLength() < 30) {
				value1 += key.toString();
			} else if (key.getLength() > 30 && key.getLength() < 50) {
				value1 += key.toString();

				if (!key1.isEmpty()) {
					StringTokenizer tokenizer = new StringTokenizer(key1, ",");

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
						key1 = "3. Library Names -->";
						value1 = Name + value1;
						if (!Sun.contains("closed")) {
							key1 = "2. Sunday Open -->";
							value1 = Name + value1;
						}
						if (Mon.contains("closed") && Tue.contains("closed")
								&& Wed.contains("closed")
								&& Thu.contains("closed")
								&& Fri.contains("closed")
								&& Sat.contains("closed")
								&& Sun.contains("closed")) {
							key1 = "1. Not Working Anymore -->";
							value1 = Name + value1;
						}
					} else {
						key1 = "3. Library Names -->";
						Name = tokenizer.nextToken();
						value1 = Name + value1;
					}
				}
				output.collect(new Text(key1), new Text(value1));
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

			String allLibraries = "";
			String finalkey = "";

			if (key.toString().contains("1. Not Working Anymore")) {
				while (values.hasNext()) {
					totalcount1++;
					allLibraries += values.next().toString() + "\n";
				}
				finalkey = key.toString() + Integer.toString(totalcount1)
						+ "\n";
				output.collect(new Text(finalkey), new Text(allLibraries));
			}
			if (key.toString().contains("2. Sunday Open")) {
				while (values.hasNext()) {
					totalcount2++;
					allLibraries += values.next().toString() + "\n";
				}
				finalkey = key.toString() + Integer.toString(totalcount2)
						+ "\n";
				output.collect(new Text(finalkey), new Text(allLibraries));
			}
			if (key.toString().contains("3. Library Names")|key.equals(" ")) {
				while (values.hasNext()) {
					totalcount3++;
					allLibraries += values.next().toString() + "\n";
				}
				totalcount = totalcount1 + totalcount2 + totalcount3;
				finalkey = key.toString() + Integer.toString(totalcount) + "\n";
				output.collect(new Text(finalkey), new Text(allLibraries));
			}

		}
	}

	public static void main(String[] args) throws Exception {
		JobConf conf = new JobConf(LibraryFull.class);
		conf.setJobName("LibraryFull");

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
