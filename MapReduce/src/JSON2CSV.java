import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSON2CSV {
  public static void main(String myHelpers[]) throws JSONException, IOException{
     String jsonString = "{\"infile\": "
     		+ "[{\"field1\": 11,\"field2\": 12,\"field3\": 13},"
     		+ "{\"field1\": 21,\"field2\": 22,\"field3\": 23},"
     		+ "{\"field1\": 31,\"field2\": 32,\"field3\": 33}]}";

     JSONObject output = new JSONObject(jsonString);
     JSONArray docs = output.getJSONArray("field1");
     //JSONObject output = new JSONObject();
     //JSONArray docs = new JSONArray(jsonString);
     
     File file=new File("/Users/subratagarai/testJSON/fromJSON.csv");
     String csv = CDL.toString(docs);
     FileUtils.writeStringToFile(file, csv);
  }
}