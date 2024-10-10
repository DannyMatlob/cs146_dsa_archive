
public class test {
	
	public static int add(int a, int b) {
		int value1 = a;
		int value2 = b;
		return value1 + value2;
	}
	
	public static void main(String args[]) {
		System.out.println("First this statement will print");
		System.out.println("Then this one is going to print");
		int number = add(1,2);
		System.out.println(number);
	}
}
