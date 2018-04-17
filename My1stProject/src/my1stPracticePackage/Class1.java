package my1stPracticePackage;

//import java.util.Arrays;

public class Class1 {

	// static int st1 = 100;

	// int arr1 [] = new int [5];
	// int arr2 [] = new int [5];
	//
	// for (int i=0; i<5; i++){
	// arr1[i] = i+300;
	// }
	// System.out.println(Arrays.toString(arr1));
	//
	//
	// Class1 obj1 = new Class1();
	// obj1.st1 = 200;
	// System.out.println(obj1.st1);
	// arr2 = obj1.revOrder(arr1);
	// System.out.println(Arrays.toString(arr2));
	// //}

	// public class class2{
	

	public int[] revOrder(int ar[]) {
		int arb[] = new int[ar.length];
		int k = ar.length - 1;
		int t = 0;
		while (k >= 0) {
			arb[t] = ar[k];
			k--;
			t++;
		}
		return arb;
	}

	
}