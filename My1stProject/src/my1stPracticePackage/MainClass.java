package my1stPracticePackage;
import java.util.Arrays;

public class MainClass extends AbsClass{	
	
	
	public static void main(String[] args) {
		
		int [] prr = {500,600,700,560,78,2};
		int a1 = 1;
		
		Class2 obj_2 = new Class2();
		//System.out.println(Arrays.toString(prr));
		
		int arr2 [] = new int [prr.length];
		int arr3 [] = new int [prr.length];
		
		arr2 = obj_2.revOrder(prr);
		System.out.println(Arrays.toString(arr2));
		
		arr3 = obj_2.revOrder(prr, a1);
		System.out.println(Arrays.toString(arr3));
	//}
		
		MainClass obj_3 = new MainClass();
		obj_3.fun();
		obj_3.func();		
}
	void fun() {
		// TODO Auto-generated method stub
		System.out.println("Overriden Normal Method");
	}
	@Override
	void func() {
		// TODO Auto-generated method stub
		System.out.println("Overriden Abstract Method");
	}
}
