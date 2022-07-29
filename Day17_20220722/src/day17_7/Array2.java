package day17_7;

public class Array2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = new int[5];
		
		for(int i = 0; i < a.length; i++) {
			a[i] = 5 - i;
		}
		
		for(int j = 0; j < a.length; j++) {
			System.out.println("a[" + j + "]=" + a[j]);
		}

	}

}
