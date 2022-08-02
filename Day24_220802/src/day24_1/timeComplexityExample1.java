package day24_1;

public class timeComplexityExample1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int findNumber = (int)(Math.random() * 100);
		for(int i = 0; i < 100; i++) {
			if(i == findNumber) {
				System.out.println(i);
				break;
			}
		}
	}
}
