//package org.myorg;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

//import org.apache.hadoop.util.*;

public class MaxCR {

	public static class Map extends MapReduceBase implements
			Mapper<LongWritable, Text, Text, Text> {

		private Text word = new Text();

		public void map(LongWritable key, Text value,
				OutputCollector<Text, Text> output, Reporter reporter)
				throws IOException {
			String line = value.toString();

			String NoOfStudents = " ";
			String CRmean = " ";
			String schoolName = " ";
			String val = " ";
			//int CRmean = 0;

			StringTokenizer tokenizer = new StringTokenizer(line, ",");
			while (tokenizer.hasMoreTokens() && tokenizer.countTokens() == 6) {
				tokenizer.nextToken();
				schoolName = tokenizer.nextToken();
				NoOfStudents = tokenizer.nextToken();
				CRmean = tokenizer.nextToken();

				if (isNumeric(CRmean) && isNumeric(NoOfStudents)) {
					if (Integer.parseInt(NoOfStudents) > 5) {
						//CRmean = Integer.parseInt(temp);
						val = schoolName + ',' + CRmean;
						word.set("CR Mean");
					}
				}
			}
			output.collect(word, new Text(val));
		}
	}

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}

	public static class Reduce extends MapReduceBase implements
			Reducer<Text, Text, Text, IntWritable> {
		public void reduce(Text key, Iterator<Text> values,
				OutputCollector<Text, IntWritable> output, Reporter reporter)
				throws IOException {
			
			String schoolNm = " ";
			String schoolName = " ";
			int crMean = 0;
			int crMeanMax = 0;
			while (values.hasNext()) {
				if (values.equals(null)| values.equals(" ")){
					break;
				}
				StringTokenizer tkenizr = new StringTokenizer(values.next().toString(), ",");
				if(tkenizr.countTokens() == 2){
					schoolNm = tkenizr.nextToken();
					crMean = Integer.parseInt(tkenizr.nextToken());
				}
				if (crMeanMax < crMean){
					crMeanMax = crMean;
					schoolName = schoolNm;
				};
			}
			output.collect(new Text(schoolName), new IntWritable(crMeanMax));
		}
	}

	public static void main(String[] args) throws Exception {
		JobConf conf = new JobConf(MaxCR.class);
		conf.setJobName("MaxCR");

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
