import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		IntermediateClass obj = new IntermediateClass();
//		int v = obj.add(4, 5);
//		obj.print(v);
//		obj.metoo();

		char buffer1[] = new char[20]; 
		BufferedReader bfrRdr1 = new BufferedReader(new InputStreamReader(
				System.in));
		System.out.println("Enter Your Name:");
		//String name = "";
		try {
			bfrRdr1.read(buffer1,2,10);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Enter Your Age:");
		int age = 0;
		try {
			age = Integer.parseInt(bfrRdr1.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("I am " + Arrays.toString(buffer1) + " and I am " + age + " years old");

	}

}
