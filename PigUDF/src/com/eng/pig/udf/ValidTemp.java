package com.eng.pig.udf; 

import java.io.IOException;

 //import java.io.IOException;
 
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;
//import org.apache.pig.impl.util.WrappedIOException;

public class ValidTemp extends EvalFunc<String> {

	//@SuppressWarnings("deprecation")
	public String exec(Tuple input) throws IOException {
		if (input == null || input.size() == 0)
			return null;
		try {
			String str = (String) input.get(0);

			if (str.equals("-9999.0")) {
				return "0";
			} else {
				return str;
			}
		} catch (Exception e) {
			//throw IOException(
				//	"Caught exception processing input row ", e;
			return null;
		}
	}
}

