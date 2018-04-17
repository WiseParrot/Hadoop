public class IntermediateClass implements IntfClass1, IntfClass2 {

	public int add(int x, int y) {
		return (x + y);
	}

	public void print(int v) {
		System.out.println(v);
	}
	
//	public void meetoo(){
//		
//	}

	@Override
	public void metoo() {
		// TODO Auto-generated method stub
		System.out.println("meetoo");
	}
}
