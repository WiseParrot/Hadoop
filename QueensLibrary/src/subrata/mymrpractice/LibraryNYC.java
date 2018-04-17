package subrata.mymrpractice;

import java.io.IOException;
//import java.util.*;

import java.util.Iterator;

import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
//import org.apache.hadoop.mapred.lib.LongSumReducer;

//import org.apache.hadoop.util.*;

public class LibraryNYC {

	public static class Map extends MapReduceBase implements
			Mapper<Text, Text, Text, Text> {

		//private final static IntWritable one = new IntWritable(1);

		public void map(Text key, Text value,
				OutputCollector< Text, Text> output, Reporter reporter)
				throws IOException {
			
				output.collect(key, value);
//			}
		}
	}

//	public static class Reduce extends MapReduceBase implements
//			Reducer< Text, Text, Text, Text> {
//		
//		
//		public void reduce(Text key, Iterator<Text> values,
//				OutputCollector<Text, Text> output, Reporter reporter)
//				throws IOException {
//			int count = 0;
//			while (values.hasNext()) {
//				count ++;
//			}
//			output.collect(key, new IntWritable(count));
//		}
//	}

	public static void main(String[] args) throws Exception {
		JobConf conf = new JobConf(LibraryNYC.class);
		conf.setJobName("LibraryNYC");

		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);

		 conf.setNumReduceTasks(0);
		conf.setMapperClass(Map.class);
	//	conf.setCombinerClass(Reduce.class);
		//conf.setReducerClass(Reduce.class);

		conf.setInputFormat(KeyValueTextInputFormat.class);
	//	conf.set("mapreduce.input.keyvaluelinerecordreader.key.value.separator", "\"");
		conf.set("key.value.separator.in.input.line", ",");
		conf.setOutputFormat(TextOutputFormat.class);

		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));

		JobClient.runJob(conf);
	}
}
