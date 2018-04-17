
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) {

		char buffer1[] = new char[20]; 
		BufferedReader bfrRdr1 = new BufferedReader(new InputStreamReader(
				System.in));
		System.out.println("Enter Your Name:");
		String name = "";
		try {
			name = bfrRdr1.readLine();
			//bfrRdr1.read(buffer1,2,10);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//
//		System.out.println("Enter Your Age:");
//		int age = 0;
//		try {
//			age = Integer.parseInt(bfrRdr1.readLine());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		StringTokenizer tokenizer = new StringTokenizer (name,",");
		 tokenizer.nextToken();
		String inp2 = tokenizer.nextToken();
		
		System.out.println( " and inp2 =  " + inp2 );

	}

}
