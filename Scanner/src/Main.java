import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// File fil = new File(/Macintosh
		// HD/Users/subratagarai/Desktop/NewFile.txt);
		// File fil1 = new File(FileSystem.getProperty("user.home"),
		// "NewFile.txt");

		String desktop = System.getProperty("user.home") + "/Desktop/";
		File myFile = new File(desktop + "newFile.txt");

		// Example of BufferReader
		BufferedReader bfrRdr1 = new BufferedReader(new FileReader(myFile));

		for (int i = 1; i < 3; i++) {
			String content = bfrRdr1.readLine();
			System.out.println(content);

			// Example of StringTokenizer
			StringTokenizer tokenizer = new StringTokenizer(content);

			while(tokenizer.hasMoreTokens()){
				System.out.println(tokenizer.nextToken());
			}
		}
		// System.out.println(tokenizer.nextToken());
		bfrRdr1.close();

		// Example of scanner
		Scanner scnr = new Scanner(myFile);
		scnr.useDelimiter("/");
		String fulltext = scnr.next();
		//String str1 = scnr.next();
		//String i = scnr.next();

		System.out.print(fulltext);
		//System.out.println(i);

	}

}
