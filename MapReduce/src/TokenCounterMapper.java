
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

import javax.json.Json;
import javax.json.stream.*;

//import org.apache.hadoop.util.*;

public static class TokenCounterMapper
    extends Mapper<Object, Text, Text, IntWritable> {
    //private static final Log log = LogFactory.getLog(TokenCounterMapper.class);
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    public void map(Object key, Text value, Context context)
        throws IOException, InterruptedException {
        try {

           // JSONObject jsn = new JSONObject(value.toString());
            JsonParser parser = Json.createParser(new StringReader("[]"));
            
            //StringTokenizer itr = new StringTokenizer(value.toString());
            String text = (String) jsn.get("text");
            log.info("Logging data");
            log.info(text);
            StringTokenizer itr = new StringTokenizer(text);
            while (itr.hasMoreTokens()) {
                word.set(itr.nextToken());
                context.write(word, one);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}