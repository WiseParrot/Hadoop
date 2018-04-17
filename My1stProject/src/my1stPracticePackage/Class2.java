package my1stPracticePackage;

public class Class2 extends Class1 {
	
	//Class1 obj_1 = new Class1();
	//int ar[] = new int[5];
	
	
	public int[] revOrder(int ar[]) {
		int arb[] = new int[ar.length];
//		int k = ar.length - 1;
//		int t = 0;
//		while (k >= 0) {
//			arb[t] = ar[k];
//			k--;
//			t++;
//		}
		System.out.println("Overiden Method");
		arb = super.revOrder(ar);
		return arb;
	}
	
	
	public int[] revOrder(int ar[], int sort) {

		int store;
		// System.out.println(ar.length);
		for (int j = ar.length - 1; j > 0; j--) {
			for (int i = ar.length - 1; i > 0; i--) {
				if (ar[i] > ar[i - 1]) {
					store = ar[i];
					ar[i] = ar[i - 1];
					ar[i - 1] = store;
				}

			}
		}
		return ar;
	}

}
